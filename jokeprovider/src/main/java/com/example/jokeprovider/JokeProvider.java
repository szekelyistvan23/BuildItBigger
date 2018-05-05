package com.example.jokeprovider;

import java.util.Random;

public class JokeProvider {
    public static String[] jokes = {"Q: What do elves learn at school?\n" +
            "A: The elf-abet.",
            "Q: Why was the math book sad?\n"
                    + "A: Because it had so many problems.",
            "Q: What did the calculator say to the math student?\n" +
                    "A: You can count on me.",
            "Q: Why did the boy bring the ladder to school?\n" +
                    "A: He was going to high school.",
            "Q: Why were the teacher's eyes crossed?\n" +
                    "A: She couldn't control her pupils.",
            "Q: Why was the student's report card wet?\n" +
                    "A: It was below C level!",
            "Q: What three candies can you find in every school?\n" +
                    "A: Nerds, DumDums, and Smarties.",
            "Q: What did the buffalo say to his kid when he dropped him off at school?\n" +
                    "A: Bison (as in, \"bye, son\")!",
            "Q: What's a snake's favorite subject?\n" +
                    "A: Hisstory. ",
            "Q: Why did the teacher wear sunglasses inside?\n" +
                    "A: Her students were so bright!"};

    public static String getOneJoke() {
        Random random = new Random();
        int index  = random.nextInt(jokes.length);
        return jokes[index];
    }
}
