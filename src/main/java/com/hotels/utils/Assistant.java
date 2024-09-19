package com.hotels.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.common.function.FunctionDef;
import io.github.sashirestela.openai.common.function.FunctionExecutor;
import io.github.sashirestela.openai.common.function.Functional;
import io.github.sashirestela.openai.common.tool.ToolCall;
import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.Chat.Choice;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.AssistantMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.ResponseMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.ToolMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Assistant {

  private SimpleOpenAI openAI;
  private FunctionExecutor functionExecutor;

  private int indexTool;
  private StringBuilder content;
  private StringBuilder functionArgs;

  public Assistant() {
    openAI = SimpleOpenAI.builder().apiKey(TOKEN).build();
  }

  public void runConversation(String fileData) {
    List<ChatMessage> messages = new ArrayList<>();
    System.out.println("Welcome! Write any message or write 'exit' to finish.");
    var myMessage = "";
    fileData += "Pretend to be an hotel AI assistant, use this csv file data to answer the questions, don't mention that you have a csv file. Don't answer any question that is not related to hotels, pretend that you are only a hotel AI assistant nothing else. if someone ask anything unrelated to hotels, just say 'I'm sorry, I can only help with hotel related questions'.";
    messages.add(UserMessage.of(fileData));
    while (!myMessage.toLowerCase().equals("exit")) {
      var chatStream = openAI.chatCompletions()
          .createStream(ChatRequest.builder()
              .model("gpt-4o")
              .messages(messages)
              .stream(true)
              .build())
          .join();

      indexTool = -1;
      content = new StringBuilder();
      functionArgs = new StringBuilder();

      var response = getResponse(chatStream);

      if (response.getMessage().getContent() != null) {
        messages.add(AssistantMessage.of(response.getMessage().getContent()));
      }
      if (response.getFinishReason().equals("tool_calls")) {
        messages.add(response.getMessage());
        var toolCalls = response.getMessage().getToolCalls();
        var toolMessages = functionExecutor.executeAll(toolCalls,
            (toolCallId, result) -> ToolMessage.of(result, toolCallId));
        messages.addAll(toolMessages);
      } else {
        myMessage = System.console().readLine("\n\nWrite any message (or write 'exit' to finish): ");
        messages.add(UserMessage.of(myMessage));
      }
    }
  }

  private Choice getResponse(Stream<Chat> chatStream) {
    var choice = new Choice();
    choice.setIndex(0);
    var chatMsgResponse = new ResponseMessage();
    List<ToolCall> toolCalls = new ArrayList<>();

    chatStream.forEach(responseChunk -> {
      var choices = responseChunk.getChoices();
      if (choices.size() > 0) {
        var innerChoice = choices.get(0);
        var delta = innerChoice.getMessage();
        if (delta.getRole() != null) {
          chatMsgResponse.setRole(delta.getRole());
        } else if (delta.getContent() != null && !delta.getContent().isEmpty()) {
          content.append(delta.getContent());
          System.out.print(delta.getContent());
        } else if (delta.getToolCalls() != null) {
          var toolCall = delta.getToolCalls().get(0);
          if (toolCall.getIndex() != indexTool) {
            if (toolCalls.size() > 0) {
              toolCalls.get(toolCalls.size() - 1).getFunction().setArguments(functionArgs.toString());
              functionArgs = new StringBuilder();
            }
            toolCalls.add(toolCall);
            indexTool++;
          } else {
            functionArgs.append(toolCall.getFunction().getArguments());
          }
        } else {
          if (content.length() > 0) {
            chatMsgResponse.setContent(content.toString());
          }
          if (toolCalls.size() > 0) {
            toolCalls.get(toolCalls.size() - 1).getFunction().setArguments(functionArgs.toString());
            chatMsgResponse.setToolCalls(toolCalls);
          }
          choice.setMessage(chatMsgResponse);
          choice.setFinishReason(innerChoice.getFinishReason());
        }
      }
    });
    return choice;
  }

  public static void main(String[] args) {
    var demo = new Assistant();
    String fileData = CSVFile.readFile("hotel_details.csv");
    demo.runConversation(fileData);
  }
}