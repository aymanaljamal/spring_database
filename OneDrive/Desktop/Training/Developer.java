//Developer class
class Developer implements Employee {
    private String name;
    Developer(String name) { this.name = name; }

    public String getName() { return name; }
    public void work() { System.out.println(name + " is writing code..."); }
    public String getRole() { return "Developer"; }
}
