import React, {useContext, useState} from 'react';
import {Alert, Button, Grid, MenuItem, TextField} from "@mui/material";
import {PeopleContext, PropertiesContext} from "../App";
import {useLocation, useNavigate} from "react-router-dom";
import {DatePicker} from "@mui/x-date-pickers";
import {saveBookings} from "../api/api";
import moment from "moment";

const EditBooking = () => {

    const {state} = useLocation();
    const navigate = useNavigate();

    const people = useContext(PeopleContext);
    const properties = useContext(PropertiesContext);

    const [error, setError] = useState(null);

    const [booking, setBooking] = useState({
        id: !!state.id? state.id : null,
        startDate: !!state.startDate ? moment(state.startDate) : moment(new Date()),
        endDate: !!state.endDate ? moment(state.endDate) : moment(new Date()).add(3, 'days'),
        personId: !!state.personId ? state.personId : 1,
        propertyId: !!state.propertyId ? state.propertyId : 1
    });

    const handleProperty = (propertyId) => {
        setBooking(
            {
                ...booking,
                propertyId
            }
        )
    }

    const handlePerson = (personId) => {
        setBooking(
            {
                ...booking,
                personId
            }
        )
    }

    const handleStartDate = (startDate) => {
        setBooking(
            {
                ...booking,
                startDate
            }
        )
    }

    const handleEndDate = (endDate) => {
        setBooking(
            {
                ...booking,
                endDate
            }
        )
    }


    const handleSaveButton = () => {
        saveBookings(booking).then(res => {
            if(res.ok){
                navigate("/");
            } else {
                res.text().then(text => setError(text));
            }

        }).catch(err => console.log(err));
    }

    return (
        <form>
            <Grid
                container
                direction="row"
                justifyContent="center"
                alignItems="center"
                spacing={2}
            >
                <Grid item xs={12}>
                    {
                        state.id !== null ? <h1>Edit Booking</h1> : <h1>Add Booking</h1>
                    }
                </Grid>
                <Grid item xs={6}>
                    <TextField
                        id="outlined-select-people"
                        select
                        label="Person"
                        helperText="Please select the person who wants to booking"
                        value={booking.personId}
                        onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                            handlePerson(event.target.value);
                        }}
                    >
                        {people.map((person) => (
                            <MenuItem key={person.id} value={person.id}>
                                {person.name}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
                <Grid item xs={6}>
                    <TextField
                        id="outlined-select-people"
                        select
                        label="Property"
                        helperText="Please select the property you want to booking"
                        value={booking.propertyId}
                        onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                            handleProperty(event.target.value);
                        }}
                    >
                        {properties.map((property) => (
                            <MenuItem key={property.id} value={property.id}>
                                {property.name}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
                <Grid item xs={6}>
                    <DatePicker value={booking.startDate} onChange={(newValue) => handleStartDate(newValue)} label="Start Date"/>
                </Grid>
                <Grid item xs={6}>
                    <DatePicker value={booking.endDate} onChange={(newValue) => handleEndDate(newValue)} label="End Date"/>
                </Grid>
                <Grid item xs={12}>
                    <Button variant="contained" onClick={handleSaveButton}>Save</Button>
                </Grid>
                <Grid item xs={6}>
                    {
                        !!error && <Alert variant="filled" severity="error">{error}</Alert>
                    }
                </Grid>
            </Grid>
        </form>
    )
}

export default EditBooking;
