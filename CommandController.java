package hello.controller;

import hello.Command;

import org.apache.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

@Controller
public class CommandController {

    private static Logger logger = Logger.getLogger(CommandController.class.getName());

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String commandForm(Model model) {
        model.addAttribute("cmd", new Command());
        return "execution";
    }

    @PostMapping("/")
    public String commandSubmit(@ModelAttribute Command cmd, Model model) throws IOException {

        ArrayList<String> result = executeCommand(cmd.getCmdName());
        cmd.setCmdResult(result);
        model.addAttribute("cmd", cmd);

        logger.info(String.format("Command: \"%s\"; Result: \"%s\"", cmd.getCmdName(), cmd.getCmdResult().toString()));

        return "result";

    }

    private ArrayList<String> executeCommand(String commandName) throws IOException{

            //Вызываем переданную команду, по умолчанию 'help'
        String line;
        ArrayList<String> result = new ArrayList<>();
        Process cmdProc = Runtime.getRuntime().exec(commandName);


        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(
                cmdProc.getInputStream(), Charset.forName("CP866")));
            //получаем stdout
        while ((line = stdoutReader.readLine()) != null) {
            result.add(line);
        }
        stdoutReader.close();


            //если возникла ошибка, stdout будет пустой, получаем stderr
        if (result.isEmpty()){
            BufferedReader srterrReader = new BufferedReader(new InputStreamReader(
                    cmdProc.getErrorStream(), Charset.forName("CP866")));

            while ((line = srterrReader.readLine()) != null) {
                    // process procs error output here
                result.add(line);
            }
            srterrReader.close();
        }

        return result;
    }

}
