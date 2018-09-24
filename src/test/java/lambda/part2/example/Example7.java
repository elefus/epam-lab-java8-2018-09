package lambda.part2.example;

import lambda.data.Employee;
import lambda.data.Person;

import java.util.ArrayList;
import java.util.List;

public class Example7 {

    private static List<Person> selectPeopleOlderThan30(List<Employee> employees) {
        ArrayList<Person> result = new ArrayList<>();
        for (Employee employee : employees) {
            Person person = employee.getPerson();
            if (person.getAge() > 30) {
                result.add(person);
            }
        }
        return result;
    }

//    private static List<Person> selectPeopleOlderThan40(List<Employee> employees) {
//        ArrayList<Person> result = new ArrayList<>();
//        for (Employee employee : employees) {
//            Person person = employee.getPerson();
//            if (person.getAge() > 40) {
//                result.add(person);
//            }
//        }
//        return result;
//    }

//    private static List<Person> selectAllAlexes(List<Employee> employees) {
//        ArrayList<Person> result = new ArrayList<>();
//        for (Employee employee : employees) {
//            Person person = employee.getPerson();
//            if ("Alex".equals(person.getFirstName())) {
//                result.add(person);
//            }
//        }
//        return result;
//    }

//    private static List<Person> selectAllAlexesOlderThan30(List<Employee> employees) {
//        ArrayList<Person> result = new ArrayList<>();
//        for (Employee employee : employees) {
//            Person person = employee.getPerson();
//            if ("Alex".equals(person.getFirstName() && person.getAge() > 30)) {
//                result.add(person);
//            }
//        }
//        return result;
//    }
}
