package task5;

import task5.services.HotelService;

public class Main {
    public static void main(String[] args) {
        HotelService hotel = new HotelService();
        HotelController controller = new HotelController(hotel);
        HotelView view = new HotelView(controller);
        view.start();
    }
}
