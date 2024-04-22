import java.util.*;

class Zoo {
    private String name;

    public Zoo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void performCommand(String command) {
        System.out.println(name + " не знает, как выполнить команду: " + command);
    }
}

class Pet extends Zoo {
    public Pet(String name) {
        super(name);
    }

    @Override
    public void performCommand(String command) {
        switch (command.toLowerCase()) {
            case "сидеть":
                System.out.println(getName() + " сел");
                break;
            case "лежать":
                System.out.println(getName() + " лег");
                break;
            case "лазать":
                System.out.println(getName() + " лазает по дому");
                break;
            default:
                super.performCommand(command);
        }
    }
}

class Dog extends Pet {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void performCommand(String command) {
        switch (command.toLowerCase()) {
            case "лови":
                System.out.println(getName() + " начал ловить мяч");
                break;
            default:
                super.performCommand(command);
        }
    }
}

public class AnimalRegistry {
    public static void main(String[] args) {
        try (Counter counter = new Counter()) {
            Scanner scanner = new Scanner(System.in);
            List<Zoo> animals = new ArrayList<>();

            while (true) {
                System.out.println("Меню:");
                System.out.println("1. Завести новое животное");
                System.out.println("2. Увидеть список команд, которые выполняет животное");
                System.out.println("3. Обучить животное новым командам");
                System.out.println("4. Выход");

                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Введите имя нового животного: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите тип животного (собака, кошка, хомяк): ");
                        String type = scanner.nextLine();

                        Zoo newAnimal = createAnimal(name, type);
                        if (newAnimal != null) {
                            animals.add(newAnimal);
                            counter.add();
                        }
                        break;
                    case 2:
                        System.out.println("Список команд, которые могут выполнять животные:");
                        System.out.println("Сидеть, Лежать, Лазать (для домашних животных)");
                        System.out.println("Лови (для собак)");
                        break;
                    case 3:
                        System.out.print("Введите имя животного, которому хотите добавить команду: ");
                        String animalName = scanner.nextLine();
                        Zoo animal = findAnimal(animals, animalName);
                        if (animal != null) {
                            System.out.print("Введите новую команду для животного: ");
                            String newCommand = scanner.nextLine();
                            animal.performCommand(newCommand);
                        } else {
                            System.out.println("Животное с таким именем не найдено");
                        }
                        break;
                    case 4:
                        System.out.println("Выход из программы");
                        return;
                    default:
                        System.out.println("Некорректный выбор");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Zoo createAnimal(String name, String type) {
        switch (type.toLowerCase()) {
            case "собака":
                return new Dog(name);
            case "кошка":
                return new Pet(name);
            case "хомяк":
                return new Pet(name);
            default:
                System.out.println("Неизвестный тип животного");
                return null;
        }
    }

    private static Zoo findAnimal(List<Zoo> animals, String name) {
        for (Zoo animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }
}

class Counter implements AutoCloseable {
    private int count;

    public Counter() {
        this.count = 0;
    }

    public void add() {
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void close() throws Exception {
        if (count > 0) {
            throw new IllegalStateException("Работа с объектом типа Счетчик должна быть в ресурсном блоке try");
        }
    }
}
