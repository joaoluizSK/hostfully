import {render, screen} from "@testing-library/react";
import BookingComponent from "./BookingComponent";
import * as router from 'react-router';

const navigate = jest.fn()

beforeEach(() => {
    jest.spyOn(router, 'useNavigate').mockImplementation(() => navigate)
});

test('renders the app title', () => {
    render(<BookingComponent />);
    const linkElement = screen.getByText(/Bookings/i);
    expect(linkElement).toBeInTheDocument();
});

test('renders the Add new booking button', () => {
    render(<BookingComponent />);
    const linkElement = screen.getByText(/Add new Booking/i);
    expect(linkElement).toBeInTheDocument();
});
