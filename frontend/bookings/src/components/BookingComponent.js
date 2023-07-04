import React, {useContext} from 'react';
import {
    Button, Grid,
    Paper,
    Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow
} from "@mui/material";
import {useEffect, useState} from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import moment from "moment";
import {deleteBooking} from "../api/api";
import {PeopleContext, PropertiesContext} from "../App";
import {useNavigate} from "react-router-dom";

const BookingComponent = () => {

    const [bookings, setBookings] = useState([]);
    const people = useContext(PeopleContext);
    const properties = useContext(PropertiesContext);
    const navigate = useNavigate();

    useEffect(() => {
        queryBookings();
    }, []);

    const queryBookings = () => {
        fetch('http://localhost:8080/bookings')
            .then(response => response.json())
            .then(data => setBookings(data));
    }

    const handleDeleteBooking = (booking) => {
        deleteBooking(booking.id).then(() => queryBookings())
    }

    const handleCreateBooking = () => {
        navigate('/edit/', {state: {}});
    }

    const handleEditBooking = (booking) => {
        navigate('/edit/', {state: {...booking}});
    }

    return (
        <Grid
            container
            direction="row"
            justifyContent="center"
            alignItems="center"
        >
            <Grid item xs={8}>
                <h1>Bookings</h1>
            </Grid>
            <Grid item xs={4}>
                <Button variant="contained" onClick={handleCreateBooking}>Add new Booking</Button>
            </Grid>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 650}} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Property</TableCell>
                            <TableCell align="right">Person</TableCell>
                            <TableCell align="right">Start Date</TableCell>
                            <TableCell align="right">End Date</TableCell>
                            <TableCell align="right">Edit</TableCell>
                            <TableCell align="right">Delete</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {bookings.map((booking) => (
                            <TableRow
                                key={booking.id}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {properties.find(prop => prop.id === booking.propertyId)?.name || ""}
                                </TableCell>
                                <TableCell
                                    align="right">{people.find(person => person.id === booking.personId)?.name || ""}</TableCell>
                                <TableCell
                                    align="right">{moment(new Date(booking.startDate)).format('MMMM Do YYYY')}</TableCell>
                                <TableCell
                                    align="right">{moment(new Date(booking.endDate)).format('MMMM Do YYYY')}</TableCell>
                                <TableCell align="right"><Button variant="outlined"
                                                                 onClick={() => handleEditBooking(booking)}>
                                    <EditIcon></EditIcon>
                                    </Button>
                                </TableCell>
                                <TableCell align="right"><Button variant="outlined"
                                                                 onClick={() => handleDeleteBooking(booking)}>
                                    <DeleteIcon></DeleteIcon>
                                </Button></TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Grid>
    )
}

export default BookingComponent;
