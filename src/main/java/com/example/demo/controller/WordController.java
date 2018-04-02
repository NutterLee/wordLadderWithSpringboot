package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

//todo 按照url传入两个参数
//使得mainPart返回目标string


@Controller
public class WordController {
    //public @ResponseBody String say(){
        //return "hellospringboot";
    //}
    @RequestMapping("/mainPart")
    public @ResponseBody static String mainPart(String start,String end) throws IOException {
        // write your code here
        Set<String> dictionary = new HashSet<>();
        String root = System.getProperty("user.dir");
        String filename = "dictionary.txt";
        String filePath = root+"\\src\\main\\java\\com\\example\\demo\\controller\\"+filename;
        String word1;
        String word2;
        Scanner sc = new Scanner(System.in);
        //read the dictionary file and store the words in the set
        BufferedReader dic_reader= new BufferedReader(new FileReader(filePath));
        String tmpword = null;

        while((tmpword=dic_reader.readLine())!=null)
        {
            dictionary.add(tmpword);

        }
        //
        Queue<Stack<String>> words = new LinkedBlockingQueue<>();
        Stack<String> final_result = new Stack<>();
        Set<String> word_collection = new HashSet<>();
        //get word1 and word2
        //System.out.println("Word #1 (or Enter to quit):");
        //word1 = sc.nextLine();
        word1=start;
        if(word1.equals(""))
        {
            System.out.println("Have a nice day.");
            return "Have a nice day.";
        }
        //System.out.println("Word #2 (or Enter to quit):");
        //word2 = sc.nextLine();
        word2=end;
        if(word2.equals(""))
        {
            System.out.println("Have a nice day.");
            return "Have a nice day.";
        }

        //error handling
        if(word1.equals(word2) || dictionary.contains(word1)==false||dictionary.contains(word2)==false)
        {
            System.out.println("word error");
            return "word error";
        }

        //build the ladder
        Stack<String> word_container = new Stack<>();
        boolean get_result = false;
        word_container.push(word1);
        words.offer(word_container);

        while (!words.isEmpty())
        {

            Stack<String> tmp_stack = words.poll();
            String tmp_word = tmp_stack.peek();

            for (int i = 0; i < tmp_word.length() ; i++) {
                for(char character= 'a';character<='z';character++)
                {
                    String new_word = tmp_word.replace(tmp_word.charAt(i),character);
                    if(word2.equals(new_word))
                    {
                        get_result=true;
                        tmp_stack.push(new_word);
                        while (!tmp_stack.isEmpty())
                        {
                            final_result.push(tmp_stack.pop());
                        }
                        break;
                    }
                    if(!dictionary.contains(new_word)||new_word.equals(tmp_word)||word_collection.contains(new_word)) continue;
                    word_collection.add(new_word);
                    Stack<String> tmp_stack2 = (Stack<String>) tmp_stack.clone();
                    tmp_stack2.push(new_word);
                    words.offer(tmp_stack2);
                }

            }
            if(get_result==true) break;
        }
        if(final_result.isEmpty()) return "the ladder does not exist.";
        else
        {
            String result="A ladder from "+word1+" to "+word2+": ";
            while (!final_result.isEmpty())
            {
                result+=(final_result.pop()+" ");
            }
            return result;
        }

    }

}
