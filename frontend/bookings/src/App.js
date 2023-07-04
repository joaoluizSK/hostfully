import './App.css';

import {BrowserRouter, Route, Routes} from "react-router-dom";
import BookingComponent from "./components/BookingComponent";
import EditBooking from "./components/EditBooking";
import {createContext, useEffect, useState} from "react";
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterMoment } from '@mui/x-date-pickers/AdapterMoment'


export const PeopleContext = createContext([]);
export const PropertiesContext = createContext([]);

function App() {

    const [people, setPeople] = useState([]);
    const [properties, setProperties] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/people')
            .then(response => response.json())
            .then(data => setPeople(data));

        fetch('http://localhost:8080/properties')
            .then(response => response.json())
            .then(data => setProperties(data));

    }, []);

    return (
        <div className="App">
            <PropertiesContext.Provider value={properties}>
                <PeopleContext.Provider value={people}>
                    <LocalizationProvider dateAdapter={AdapterMoment}>
                        <BrowserRouter>
                            <Routes>
                                <Route path="/" exact element={<BookingComponent/>}/>
                                <Route path="edit" element={<EditBooking/>}/>
                            </Routes>
                        </BrowserRouter>
                    </LocalizationProvider>
                </PeopleContext.Provider>
            </PropertiesContext.Provider>
        </div>
    );
}

export default App;
