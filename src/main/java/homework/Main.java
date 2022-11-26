package homework;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            String wybor = "";
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Wybierz: Dodaj/Usun/Lista/Szukaj/Aktualizuj lub wyjdz");
                wybor = scanner.nextLine();
                if (wybor.equalsIgnoreCase("dodaj")) {
                    Transaction transaction = session.beginTransaction();
                    System.out.println("Podaj marke");
                    String brand = scanner.nextLine();
                    System.out.println("Podaj moc silnika");
                    String shp = scanner.nextLine();
                    double hp = Double.parseDouble(shp);
                    System.out.println("Podaj kolor");
                    String colour = scanner.nextLine();
                    int year = 0;
                    do {
                        System.out.println("Podaj rok produkcji (od 1990 do 2020)");
                        String syear = scanner.nextLine();
                        year = Integer.parseInt(syear);
                    }
                    while (year < 1990 || year > 2020);
                    System.out.println("Czy ma naped elektryczny (true/false)");
                    boolean el = scanner.nextBoolean();
                    Vehicle vehicle = Vehicle.builder()
                            .brand(brand)
                            .horsepower(hp)
                            .colour(colour)
                            .year(year)
                            .electric(el).build();
                    session.persist(vehicle);
                    transaction.commit();
                    break;
                } else if (wybor.equalsIgnoreCase("usun")) {
                    Transaction transaction = session.beginTransaction();
                    System.out.println("Podaj id pojazdu");
                    Long id = scanner.nextLong();
                    Vehicle vehicle = session.get(Vehicle.class, id);
                    if (vehicle != null){
                        session.remove(vehicle);
                        transaction.commit();
                    } else{
                        System.err.println("Nie ma takiego pojazdu");
                    }
                    break;
                } else if (wybor.equalsIgnoreCase("lista")) {
                    TypedQuery<Vehicle> zapytanie = session.createQuery("from Vehicle", Vehicle.class);
                    List<Vehicle> vehicleList = zapytanie.getResultList();
                    for (Vehicle vehicle : vehicleList) {
                        System.out.println(vehicle);
                    }
                    break;
                } else if (wybor.equalsIgnoreCase("szukaj")) {
                    System.out.println("Podaj id pojazdu");
                    long id = scanner.nextLong();
                    Vehicle vehicle = session.get(Vehicle.class, id);
                    if (vehicle == null){
                        System.err.println("Nie znaleziono pojazdu");}
                    else {
                        System.out.println("Znalezlismy pojazd: " + vehicle);
                    }
                    break;
                } else if (wybor.equalsIgnoreCase("aktualizuj")) {
                    Transaction transaction = session.beginTransaction();
                    System.out.println("Podaj id pojazdu");
                    String id = scanner.nextLine();
                    long carId = Long.parseLong(id);
                    Vehicle carCheck = session.get(Vehicle.class, carId);
                    if (carCheck == null){
                        System.err.println("Nie ma takiego pojazdu");
                        break;}
                    System.out.println("Podaj marke");
                    String brand = scanner.nextLine();
                    System.out.println("Podaj moc silnika");
                    String shp = scanner.nextLine();
                    double hp = Double.parseDouble(shp);
                    System.out.println("Podaj kolor");
                    String colour = scanner.nextLine();
                    int year = 0;
                    do {
                        System.out.println("Podaj rok produkcji (od 1990 do 2020)");
                        String syear = scanner.nextLine();
                        year = Integer.parseInt(syear);
                    }
                    while (year < 1990 || year > 2020);
                    System.out.println("Czy ma naped elektryczny (true/false)");
                    boolean el = scanner.nextBoolean();
                    Vehicle vehicle = Vehicle.builder()
                            .id(carId)
                            .brand(brand)
                            .horsepower(hp)
                            .colour(colour)
                            .year(year)
                            .electric(el).build();
                    session.merge(vehicle);
                    transaction.commit();
                    break;
                } else if (wybor.equalsIgnoreCase("wyjdz")) {
                    System.out.println("Zatrzymano działanie");
                    break;
                }

            } while (!wybor.equalsIgnoreCase("dodaj")
                    || !wybor.equalsIgnoreCase("usun")
                    || !wybor.equalsIgnoreCase("lista")
                    || !wybor.equalsIgnoreCase("aktualuzuj")
                    || !wybor.equalsIgnoreCase("szukaj")
                    || !wybor.equalsIgnoreCase("wyjdz"));
        } catch (Exception e) {
            System.err.println("blad bazy: " + e);

        }
    }
}