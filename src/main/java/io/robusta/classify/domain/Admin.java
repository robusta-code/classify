package io.robusta.classify.domain;

/**
 * Created by dev on 29/08/14.
 */
public class Admin extends User {

    String statement;

    public Admin(String statement) {
        this.statement = statement;
    }


    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
