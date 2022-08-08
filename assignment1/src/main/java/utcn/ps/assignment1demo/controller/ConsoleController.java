package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.Tag;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;
import utcn.ps.assignment1demo.exception.QuestionNotFoundException;
import utcn.ps.assignment1demo.service.*;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
// Command line runners are executed by Spring after the initialization of the app has been done
// https://www.baeldung.com/spring-boot-console-app
public class ConsoleController implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final QuestionService questionService;
    private final ClientService clientService;
    private final TagService tagService;
    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;
    private final VoteQuestionService voteQuestionService;
    private boolean loggedIn = false;
    private Client clientLoggedIn;

    @Override
    public void run(String... args) {
        print("Welcome to my awesome reinterpretation of StackOverflow.");
        boolean done = false;
        /*while (!done) {
            print("Enter a command: ");
            String command = scanner.nextLine();
            try {
                done = handleCommand(command);
            } catch (QuestionNotFoundException questionNotFoundException) {
                print("The question with the given ID was not found!");
            }
        }*/
    }

/*    private boolean handleCommand(String command) {
        if (loggedIn) {
            switch (command) {
                case "help":
                    print(handleHelp());
                    return false;
                case "logout":
                    this.loggedIn = false;
                    clientService.insert(clientLoggedIn);
                    print("Logged out successfully");
                    return false;
                case "search question":
                    handleSearchQuestion();
                    return false;
                case "add question":
                    handleAddQuestion();
                    return false;
                case "view questions":
                    handleList();
                    return false;
                case "view tags":
                    handleTags();
                    return false;
                case "edit answer":
                    handleEditAnswer();
                    return false;
                case "delete answer":
                    handleDeleteAnswer();
                    return false;
                case "exit":
                    return true;
                default:
                    print("Unknown command. Enter \"help\" in order to see all commands");
                    return false;
            }
        } else {
            switch (command) {
                case "help":
                    print(handleHelp());
                    return false;
                case "login":
                    this.loggedIn = handleLogin();
                    return false;
                case "sign in":
                    handleSignIn();
                    return false;
                case "exit":
                    return true;
                default:
                    print("Unknown command. Enter \"help\" in order to see all commands");
                    return false;
            }
        }
    }

    private String handleHelp() {
        String s = "1. help \n";
        if (loggedIn) {
            s += "2. logout \n";
            s += "4. exit \n";
            s += "5. search question\n";
            s += "6. add question\n";
            s += "7. view questions\n";
            s += "8. modify question\n";
            s += "9. edit answer \n";
            s += "10. delete answer \n";

        } else {
            s += "2. login \n";
            s += "3. sign in \n";
            s += "4. exit \n";
        }
        return s;
    }

    public Boolean handleLogin() {
        print("Username: ");
        String username = scanner.nextLine();
        print("Password: ");
        String password = scanner.nextLine();

        List<Client> clients = clientService.findAll();
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                print("Logged in successfully");
                this.clientLoggedIn = client;
                return true;
            }
        }
        print("Wrong username or password!");
        return false;
    }

    public void handleSignIn() {
        print("Username: ");
        String username = scanner.nextLine();
        print("Password: ");
        String password = scanner.nextLine();
        Client client = new Client(username, password);
        clientService.insert(client);
        print("Signed in successfully");
    }

    private void handleSearchQuestion() {
        boolean input = true;
        while (input) {
            print("Title or tag search ? (title/tag)");
            String titleTagSearch = scanner.nextLine();
            switch (titleTagSearch) {
                case "title" -> {
                    input = false;
                    print("Title to search: ");
                    String titleSearch = scanner.nextLine();
                    Stream<Question> filteredQuestions = questionService.findAll().stream().filter(
                            question -> question.getTitle().toLowerCase().contains(titleSearch.toLowerCase())
                    );
                    List<Question> questions = filteredQuestions.toList();
                    if (!questions.isEmpty()) {
                        yeet(questions);
                    } else {
                        print("Such question doesn't exist! Do you wish to add it yourself?(y/n)");
                        String addQuestion = scanner.nextLine();
                        switch (addQuestion) {
                            case "y":
                                handleAddQuestion();
                                break;
                            case "n":
                                break;
                            default:
                                print("Wrong input!");
                                break;
                        }
                    }
                }
                case "tag" -> {
                    input = false;
                    print("Tag: ");
                    String tagSearch = scanner.nextLine();
                    Stream<Question> filteredQuestions2 = questionService.findAll().stream().filter(
                            question -> !question.getTags().stream().filter(
                                    tag -> tag.getTag().toLowerCase().contains(tagSearch)
                            ).toList().isEmpty()
                    );
                    List<Question> questions2 = filteredQuestions2.toList();
                    if (!questions2.isEmpty()) {
                        yeet(questions2);
                    } else {
                        print("Such question with this tag doesn't exist!");
                    }
                }
                default -> print("Wrong input!");
            }
        }
    }

    private void yeet(List<Question> questions2) {
        IntStream.range(0, questions2.size()).forEach(index -> print(index + ": " + questions2.get(index)));
        boolean input2 = true;
        while (input2) {
            print("Do want to add an answer to one of the questions? (y/n)");
            String inputString = scanner.nextLine();
            switch (inputString) {
                case "y" -> {
                    input2 = false;
                    print("Enter the index of the question you want to answer to: ");
                    inputString = scanner.nextLine();
                    try {
                        int owo = Integer.parseInt(inputString);
                        handleAddAnswer(questions2.get(owo).getQuestionId());

                    } catch (NumberFormatException e) {
                        print("Wrong input!");
                    }
                }
                case "n" -> input2 = false;
                default -> print("Wrong input!");
            }
        }
        input2 = true;
        while (input2) {
            print("View question? (y/n)");
            String inputString = scanner.nextLine();
            switch (inputString) {
                case "y" -> {
                    input2 = false;
                    print("Enter the index of the question you want to see: ");
                    inputString = scanner.nextLine();
                    try {
                        int owo = Integer.parseInt(inputString);
                        handleViewQuestion(questions2.get(owo));

                    } catch (NumberFormatException e) {
                        print("Wrong input!");
                    }
                }
                case "n" -> input2 = false;
                default -> print("Wrong input!");
            }
        }
    }

    private void handleList() {
        List<Question> questions = questionService.findAll();
        questions.sort(Comparator.comparing(Question::getCreationDate).reversed());
        questions.forEach(question -> print(question.toString()));
    }

    private void handleTags() {
        tagService.findAll().forEach(tag -> print(tag.toString()));
    }

    private void handleViewQuestion(Question question) {
        print(question.toString());
        question.setAnswers(questionService.findById(question.getQuestionId()).getAnswers());
        if(!question.getAnswers().isEmpty()) {
            Collections.sort(question.getAnswers());
            IntStream.range(0, question.getAnswers().size()).forEach(index -> print(index + ": " + question.getAnswers().get(index).toString()));
            print("Do you want to vote any of the answers? (y/n)");
            String input = scanner.nextLine();
            switch (input) {
                case "y":
                    print("Select the index of the answer you want to vote: ");
                    input = scanner.nextLine();
                    try {
                        int owo = Integer.parseInt(input);
                        if (!clientService.findById(clientLoggedIn.getUserId()).getAnswers().contains(question.getAnswers().get(owo))) {
                            print("Vote? (up/down) ");
                            input = scanner.nextLine();
                            switch (input) {
                                case "up" -> {
                                    question.getAnswers().get(owo).setUpVote(question.getAnswers().get(0).getUpVote() + 1);
                                    question.getAnswers().get(owo).setVoteAnswer(new VoteAnswer(clientLoggedIn.getUserId(), question.getAnswers().get(owo).getAnswerId()));
                                    answerService.update(question.getAnswers().get(owo));
                                    question.getAnswers().get(owo).setVoteAnswer(null);
                                }
                                case "down" -> {
                                    question.getAnswers().get(owo).setDownVote(question.getAnswers().get(0).getDownVote() + 1);
                                    question.getAnswers().get(owo).setVoteAnswer(new VoteAnswer(clientLoggedIn.getUserId(), question.getAnswers().get(owo).getAnswerId()));
                                    answerService.update(question.getAnswers().get(owo));
                                    question.getAnswers().get(owo).setVoteAnswer(null);
                                }
                                default -> print("Wrong input!");
                            }
                        }
                        else {
                            print("You cannot vote your own answer!");
                        }

                    } catch (NumberFormatException e) {
                        print("Wrong input!");
                    }
                    break;
                case "n":
                    break;
                default:
                    print("Wrong input!");
                    break;
            }
        }
        print("Do you want to vote this question? (y/n)");
        String input = scanner.nextLine();
        switch (input) {
            case "y":
                if (!clientService.findById(clientLoggedIn.getUserId()).getQuestions().contains(question)) {
                    print("Vote? (up/down) ");
                    input = scanner.nextLine();
                    switch (input) {
                        case "up" -> {
                            question.setUpVote(question.getUpVote() + 1);
                            question.setVoteQuestion(new VoteQuestion(clientLoggedIn.getUserId(), question.getQuestionId()));
                            questionService.update(question);
                            question.setVoteQuestion(null);
                        }
                        case "down" -> {
                            question.setDownVote(question.getDownVote() + 1);
                            question.setVoteQuestion(new VoteQuestion(clientLoggedIn.getUserId(), question.getQuestionId()));
                            questionService.update(question);
                            question.setVoteQuestion(null);
                        }
                        default -> print("Wrong input!");
                    }
                } else {
                    print("You cannot vote your own question!");
                }
                break;
            case "n":
                break;
            default:
                print("Wrong input!");
                break;
        }
    }

    private void handleAddQuestion() {
        print("Add question");
        print("Title:");
        String title = scanner.nextLine();
        print("Body:");
        String body = scanner.nextLine();
        Question question = new Question(clientLoggedIn.getUsername(), title, body, clientLoggedIn.getUserId());
        List<Tag> tagList = new ArrayList<>();
        boolean a = true;
        while (a) {
            print("Want to add specific tags to your question? (y/n)");
            String doTags = scanner.nextLine();
            switch (doTags) {
                case "y" -> {
                    boolean enterTag = true;
                    while (enterTag) {
                        print("Search for a tag: ");
                        String searchedTag = scanner.nextLine();
                        Stream<Tag> filteredTags = tagService.findAll().stream().filter(
                                tag -> tag.getTag().equals(searchedTag)
                        );
                        List<Tag> tags = filteredTags.toList();
                        boolean enterTag2 = true;
                        if (!tags.isEmpty()) {
                            while (enterTag2) {
                                print("Tag found. Do you want to add " + tags.get(0).getTag() + " to your question tags? (y/n)");
                                String addTag = scanner.nextLine();
                                switch (addTag) {
                                    case "y" -> {
                                        tagList.add(new Tag(tags.get(0).getTagId(), tags.get(0).getTag(), tags.get(0).getQuestions()));
                                        print("Tag added to your question!");
                                        enterTag2 = false;
                                        enterTag = false;
                                    }
                                    case "n" -> {
                                        enterTag2 = false;
                                        enterTag = false;
                                    }
                                    default -> print("Wrong input!");
                                }
                            }
                        } else {
                            while (enterTag2) {
                                print("Tag " + searchedTag + " was not found. Want to add it to our tags? (y/n)");
                                String addTag = scanner.nextLine();
                                switch (addTag) {
                                    case "y" -> {
                                        Tag tag = new Tag(searchedTag);
                                        tagList.add(tag);
                                        print("Tag added to your question");
                                        enterTag2 = false;
                                        enterTag = false;
                                    }
                                    case "n" -> {
                                        enterTag2 = false;
                                        enterTag = false;
                                    }
                                    default -> print("Wrong input!");
                                }
                            }
                        }
                    }
                }
                case "n" -> a = false;
                default -> print("Wrong input!");
            }
        }


        for (Tag tag : tagList) {
            tag.addQuestion(question);
            question.addTag(tag);
            tagService.insert(tag);
        }
        questionService.insert(question);
        clientLoggedIn.addQuestion(question);
        print("Created question: " + question + ".");
    }

    private void handleAddAnswer(Integer questionId) {
        print("Add answer");
        print("Answer:");
        String body = scanner.nextLine();
        Answer answer = new Answer(clientLoggedIn.getUsername(), body, questionId, clientLoggedIn.getUserId());
        answerService.insert(answer);
        clientLoggedIn.addAnswer(answer);
    }

    private void handleEditAnswer() {
        print("Edit answer");
        if (clientLoggedIn.getAnswers().isEmpty()) {
            print("You didn't answer any question!");
        } else {
            IntStream.range(0, clientLoggedIn.getAnswers().size()).forEach(index -> print(index + ": " + clientLoggedIn.getAnswers().get(index).toString()));
            print("Enter the index of the answer you want to edit: ");
            String inputString = scanner.nextLine();
            try {
                int owo = Integer.parseInt(inputString);
                updateAnswer(clientLoggedIn.getAnswers().get(owo));

            } catch (NumberFormatException e) {
                print("Wrong input!");
            }
        }
    }

    private void updateAnswer(Answer answer) {
        print(answer.toString());
        print("Body:");
        String body = scanner.nextLine();
        answer.setBody(body);
        print("Answer edited");
        answerService.update(answer);
        for(Answer a : clientLoggedIn.getAnswers()) {
            if(Objects.equals(a.getAnswerId(), answer.getAnswerId())) {
                a.setBody(body);
                break;
            }
        }

    }

    private void handleDeleteAnswer() {
        print("Delete answer");
        if (clientLoggedIn.getAnswers().isEmpty()) {
            print("You didn't answer any question!");
        } else {
            IntStream.range(0, clientLoggedIn.getAnswers().size()).forEach(index -> print(index + ": " + clientLoggedIn.getAnswers().get(index).toString()));
            print("Enter the index of the answer you want to delete: ");
            String inputString = scanner.nextLine();
            try {
                int owo = Integer.parseInt(inputString);
                deleteAnswer(clientLoggedIn.getAnswers().get(owo));

            } catch (NumberFormatException e) {
                print("Wrong input!");
            }
        }
    }

    private void deleteAnswer(Answer answer) {
        print(answer.toString());
        answerService.remove(answer.getAnswerId());
        for(Answer a : clientLoggedIn.getAnswers()) {
            if(Objects.equals(a.getAnswerId(), answer.getAnswerId())) {
                clientLoggedIn.getAnswers().remove(a);
                break;
            }
        }
        print("Answer deleted");
    }*/

    private void print(String value) {
        System.out.println(value);
    }
}
