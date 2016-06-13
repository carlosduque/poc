package o.school.faculty;

import java.util.List;
import java.util.ArrayList;

class Exam {
    private List _questions;
    private List _answers;

    Exam(List questions, List answers) {
        this._questions = questions;
        this._answers = answers;
    }

    String getQuestion(int i) {
        return (String) this._questions.get(i);
    }

    String getAnswer(int i) {
        return (String) this._answers.get(i);
    }

}
