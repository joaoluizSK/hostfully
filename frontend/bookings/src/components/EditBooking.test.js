import {render, screen} from "@testing-library/react";
import * as router from 'react-router';
import EditBooking from "./EditBooking";
import {AdapterMoment} from "@mui/x-date-pickers/AdapterMoment";
import {LocalizationProvider} from "@mui/x-date-pickers";

const navigate = jest.fn()
const location = jest.fn().mockReturnValue({state: {}})

beforeEach(() => {
    jest.spyOn(router, 'useNavigate').mockImplementation(() => navigate)

});

test('renders the component title if the value is passed as Editing', () => {
    jest.spyOn(router, 'useLocation').mockReturnValue({state: {id: -1}})

    render(<LocalizationProvider dateAdapter={AdapterMoment}>
        <EditBooking />
    </LocalizationProvider>);
    const linkElement = screen.getByText(/Edit Booking/i);
    expect(linkElement).toBeInTheDocument();
});

test('renders the component title if the value is passed as Adding', () => {
    jest.spyOn(router, 'useLocation').mockReturnValue({state: {id: null}})

    render(<LocalizationProvider dateAdapter={AdapterMoment}>
        <EditBooking />
    </LocalizationProvider>);
    const linkElement = screen.getByText(/Add Booking/i);
    expect(linkElement).toBeInTheDocument();
});
