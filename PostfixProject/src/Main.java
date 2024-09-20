import java.util.*;
public class Main {
    private static Set<String> OPSET = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

    public static void main(String[] args) {

        String test1 = "A:*:C:+:B:/:D:*:E";
        infixToPostfix(test1);
        System.out.println("Expected: A:C:*:B:D:/:E:*:+");

        String test2 = "A:-:D:*:E";
        infixToPostfix(test2);
        System.out.println("Expected: A:D:E:*:-");


        String test3 = "A:+:B:*:C:-:D:*:E";
        infixToPostfix(test3);
        System.out.println("Expected: A:B:C:*:+:D:E:*:-");

    }

    public static void evalPostfix(String input) {
        //What do we need to solve this problem?:
        //int n = Integer.parseInt(); -- make sure it's an operand
        //we should make a method that determines if it's an operator or not
        //Stack of Strings
        //Integer.toString

        String[] tokens = input.split(":");
        Stack<String> s = new Stack<>();
        for (String str : tokens) {
            if (!isOperator(str))
                s.push(str);
            else {
                int opr = Integer.parseInt(s.pop());
                int opl = Integer.parseInt(s.pop());
                switch(str) {
                    case "+":
                        s.push(opl+opr + "");
                        break;
                    case "-":
                        s.push(opl-opr+ "");
                        break;
                    case "*":
                        s.push(opl*opr+ "");
                        break;
                    case "/":
                        s.push(opl/opr+ "");
                        break;
                    default:
                        System.out.println("Operator not recognized");
                        break;
                }
            }
        }
        System.out.println(s.pop());
    }

    public static void infixToPostfix(String input) {
        Stack<String> s = new Stack<>();
        String [] tokens = input.split(":");
        String answer = "";
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("(")) {
                s.push("(");
            }
            else if (tokens[i].equals(")")) {
                while (!s.isEmpty() && !s.peek().equals("("))
                    answer += s.pop() + ":";
                s.pop();
            }
            else if (isOperator(tokens[i])) {
                while (!s.isEmpty() && !s.peek().equals("(") && !hasHigherPrecedence(tokens[i], s.peek())) {
                    answer += s.pop() + ":";
                }
                s.push(tokens[i]);
            } else {
                answer += tokens[i] + ":";
            }
        }
        while (!s.isEmpty())
            answer += s.pop() + ":";
        System.out.println(answer.substring(0, answer.length()-1));
    }


    public static boolean hasHigherPrecedence(String op1, String op2) {
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("*", 1);
        precedence.put("/", 1);
        precedence.put("+", 0);
        precedence.put("-", 0);
        return precedence.get(op1) > precedence.get(op2);
    }
    public static boolean isOperator(String input) {
        //instead of recreating the list each time, move it into the static area, it only runs once now
        //this gives it O(k) runtime
        return OPSET.contains(input);
    }
}