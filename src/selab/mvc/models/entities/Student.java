package selab.mvc.models.entities;

import selab.mvc.models.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Student implements Model {
    private String name;
    private String studentNo;
    private HashMap<Course, Integer> courses = new HashMap<>();

    @Override
    public String getPrimaryKey() {
        return this.studentNo;
    }

    public void setName(String value) { this.name = value; }
    public String getName() { return this.name; }

    public void setStudentNo(String value) {
        if (!validateStudentNo(value))
            throw new IllegalArgumentException("The format is not correct");

        this.studentNo = value;
    }
    public String getStudentNo() { return this.studentNo; }

    public float getAverage() {
        float result = 0;
        for(int point: courses.values())
            result += point;
        if(!courses.isEmpty())
            return result / courses.size();
        return 0;
    }

    public String getCourses() {
        List<Course> coursesSet = new ArrayList<>(courses.keySet());
        String result = "";
        for(int i=0; i<coursesSet.size(); i++){
            result += coursesSet.get(i).getTitle();
            if(i != coursesSet.size()-1)
                result += ',';
        }
        if(result.isEmpty())
            return "-";
        return result;
    }

    public void addCourse(Course course, int points) {
        if (courses.containsKey(course))
            throw new IllegalArgumentException("Duplicate course primary key.");

        courses.put(course, points);
    }

    /**
     *
     * @param studentNo Student number to be checeked
     * @return true, if the format of the student number is correct
     */
    private boolean validateStudentNo(String studentNo) {
        Pattern pattern = Pattern.compile("^[8-9]\\d{7}$");
        return pattern.matcher(studentNo).find();
    }

    @Override
    public int hashCode()
    {
        return getPrimaryKey().hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Student && getPrimaryKey().equals(((Student) o).getPrimaryKey());
    }
}
