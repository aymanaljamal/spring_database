import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
public class CompanySystem {
    
    static Map<Integer, Employee> employeeMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
    //=================================================================
        employeeMap.put(1, new Developer("Yazan"));
        employeeMap.put(2, new Manager("Lina"));
        employeeMap.put(3, new Developer("Sami"));

    //=================================================================
        Optional<Employee> maybeEmp = findEmployeeById(2);
        maybeEmp.ifPresent(emp -> System.out.println("Found: " + emp.getName()));

    //=================================================================    
        BonusEvaluator devBonus = (e) -> e instanceof Developer;
        employeeMap.values().forEach(e -> {
            System.out.println(e.getName() + " gets bonus? " + devBonus.hasBonus(e));
        });

    //=================================================================   
        System.out.println("\n== Developers List ==");
        List<Employee> devs = employeeMap.values().stream()
            .filter(e -> e.getRole().equals("Developer"))
            .collect(Collectors.toList());
        devs.forEach(e -> System.out.println(e.getName()));

    //=================================================================
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<String>> tasks = new ArrayList<>();

        for (Employee e : employeeMap.values()) {
            tasks.add(service.submit(() -> {
                e.work(); 
                return e.getName() + " finished working.";
            }));
        }

       
        for (Future<String> f : tasks) {
            System.out.println(f.get());
        }

        service.shutdown();
    }
 //=================================================================
    
    public static Optional<Employee> findEmployeeById(int id) {
        return Optional.ofNullable(employeeMap.get(id));
    }}