package hello;

import java.util.ArrayList;

public class Command {
    private String cmdName;
    private ArrayList<String> cmdResult;

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public ArrayList<String> getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(ArrayList<String> cmdResult) {
        this.cmdResult = cmdResult;
    }
}
