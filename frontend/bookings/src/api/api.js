export const deleteBooking = (id) => {
    return fetch(`http://localhost:8080/bookings/${id}`, {
        method: 'delete'
    });
}

export const getBookingById = (id) => {
    return fetch(`http://localhost:8080/bookings/${id}`, {
        method: 'get'
    });
}

export const saveBookings = (booking) => {
    if(!!booking.id) {
        return fetch(`http://localhost:8080/bookings/${booking.id}`, {
            body: JSON.stringify(booking),
            headers: {
                "Content-Type": "application/json",
            },
            method: 'put'
        });
    } else {
        return fetch(`http://localhost:8080/bookings`, {
            body: JSON.stringify(booking),
            headers: {
                "Content-Type": "application/json",
            },
            method: 'post'
        });
    }
}
