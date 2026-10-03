// ===============================
// KALLU COMPOUND JAVASCRIPT
// ===============================


// ===============================
// BACKEND URL
// ===============================

const API_BASE_URL = "http://localhost:8080";


// ===============================
// 1. WELCOME MESSAGE
// ===============================

window.onload = function () {

    console.log("Welcome to Kallu Compound!");

    // Load registered customers for feedback
    loadCustomersForFeedback();

    // Load prices and availability
    loadToddyData();
};


// ===============================
// 2. LOAD TODDY DATA
// ===============================

function loadToddyData() {

    fetch(API_BASE_URL + "/api/toddy")

        .then(response => {

            if (!response.ok) {

                throw new Error(
                    "Failed to load toddy data"
                );

            }

            return response.json();

        })

        .then(data => {

            for (let i = 0; i < data.length; i++) {

                let toddy = data[i];


                // ===============================
                // FRESH TODDY
                // ===============================

                if (toddy.name === "Fresh Toddy") {

                    document.getElementById(
                        "freshToddyPrice"
                    ).innerHTML =
                        "₹" + toddy.price;


                    let status =
                        toddy.available
                            ? "🟢 Available"
                            : "🔴 Not Available";


                    document.getElementById(
                        "freshToddyAvailability"
                    ).innerHTML = status;


                    document.getElementById(
                        "freshToddyStatus"
                    ).innerHTML = status;

                }


                // ===============================
                // SPECIAL TODDY
                // ===============================

                if (toddy.name === "Special Toddy") {

                    document.getElementById(
                        "specialToddyPrice"
                    ).innerHTML =
                        "₹" + toddy.price;


                    let status =
                        toddy.available
                            ? "🟢 Available"
                            : "🔴 Not Available";


                    document.getElementById(
                        "specialToddyAvailability"
                    ).innerHTML = status;


                    document.getElementById(
                        "specialToddyStatus"
                    ).innerHTML = status;

                }


                // ===============================
                // FAMILY PACK
                // ===============================

                if (toddy.name === "Family Pack") {

                    document.getElementById(
                        "familyPackPrice"
                    ).innerHTML =
                        "₹" + toddy.price;


                    let status =
                        toddy.available
                            ? "🟢 Available"
                            : "🔴 Not Available";


                    document.getElementById(
                        "familyPackAvailability"
                    ).innerHTML = status;


                    document.getElementById(
                        "familyPackStatus"
                    ).innerHTML = status;

                }

            }

        })

        .catch(error => {

            console.log(
                "Error loading toddy data:",
                error
            );

        });
}


// ===============================
// 3. DISPLAY CURRENT TIME
// ===============================

function showCurrentTime() {

    let now = new Date();

    let hours = now.getHours();

    let minutes = now.getMinutes();

    let ampm = hours >= 12 ? "PM" : "AM";


    hours = hours % 12;


    if (hours === 0) {

        hours = 12;

    }


    minutes = minutes < 10
        ? "0" + minutes
        : minutes;


    let time =
        hours + ":" + minutes + " " + ampm;


    console.log(
        "Current Time: " + time
    );
}

showCurrentTime();


// ===============================
// 4. CONTACT FORM
// ===============================

function submitContact() {

    let name =
        document.getElementById("name").value;

    let phone =
        document.getElementById("phone").value;

    let message =
        document.getElementById("message").value;


    if (name === "") {

        alert(
            "Please enter your name."
        );

        return;
    }


    if (phone === "") {

        alert(
            "Please enter your phone number."
        );

        return;
    }


    if (message === "") {

        alert(
            "Please enter your message."
        );

        return;
    }


    document.getElementById(
        "contactMessage"
    ).innerHTML =
        "Thank you " +
        name +
        "! Your message has been received.";


    document.getElementById(
        "name"
    ).value = "";

    document.getElementById(
        "phone"
    ).value = "";

    document.getElementById(
        "message"
    ).value = "";
}


// ===============================
// 5. SHOP TIMING
// ===============================

function checkShopStatus() {

    let now = new Date();

    let currentHour =
        now.getHours();

    let currentMinutes =
        now.getMinutes();


    // Opening time = 10:00 AM

    let openingHour = 10;

    let openingMinutes = 0;


    // Closing time = 10:00 PM

    let closingHour = 22;

    let closingMinutes = 0;


    let currentTime =
        (currentHour * 60) +
        currentMinutes;


    let openingTime =
        (openingHour * 60) +
        openingMinutes;


    let closingTime =
        (closingHour * 60) +
        closingMinutes;


    let status =
        document.getElementById(
            "shopStatus"
        );


    if (
        currentTime >= openingTime &&
        currentTime < closingTime
    ) {

        status.innerHTML =
            "🟢 OPEN NOW";

        status.style.color =
            "green";

    }

    else {

        status.innerHTML =
            "🔴 CLOSED NOW";

        status.style.color =
            "red";
    }
}

checkShopStatus();


// ===============================
// 6. CUSTOMER REGISTRATION
// ===============================

function registerCustomer() {

    let name =
        document.getElementById(
            "customerName"
        ).value;

    let mobile =
        document.getElementById(
            "customerMobile"
        ).value;

    let description =
        document.getElementById(
            "customerDescription"
        ).value;


    // Check name

    if (name === "") {

        alert(
            "Please enter your name."
        );

        return;
    }


    // Check mobile

    if (mobile === "") {

        alert(
            "Please enter your mobile number."
        );

        return;
    }


    // Check description

    if (description === "") {

        alert(
            "Please enter your description."
        );

        return;
    }


    // Create customer object

    let customer = {

        name: name,

        mobile: mobile,

        description: description

    };


    // Send data to Spring Boot

    fetch(
        API_BASE_URL + "/api/customers",
        {

            method: "POST",

            headers: {

                "Content-Type":
                    "application/json"

            },

            body:
                JSON.stringify(customer)

        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Registration failed"
            );

        }

        return response.json();

    })

    .then(data => {

        document.getElementById(
            "registrationMessage"
        ).innerHTML =
            "Registration successful! Welcome " +
            data.name + ".";


        // Clear form

        document.getElementById(
            "customerName"
        ).value = "";

        document.getElementById(
            "customerMobile"
        ).value = "";

        document.getElementById(
            "customerDescription"
        ).value = "";


        // Refresh feedback customer dropdown

        loadCustomersForFeedback();

    })

    .catch(error => {

        console.log(
            "Registration Error:",
            error
        );

        alert(
            "Registration failed. Please try again."
        );

    });
}


// ===============================
// 7. LOAD CUSTOMER NAMES
// ===============================

function loadCustomersForFeedback() {

    fetch(
        API_BASE_URL + "/api/customers/names"
    )

    .then(response => {

        console.log(
            "Customer names status:",
            response.status
        );

        if (!response.ok) {

            throw new Error(
                "Failed to load customer names"
            );

        }

        return response.json();

    })

    .then(customers => {

        let customerSelect =
            document.getElementById(
                "feedbackCustomer"
            );


        // Clear existing options

        customerSelect.innerHTML =
            '<option value="">Select your name</option>';


        // Add customer names

        customers.forEach(customer => {

            let option =
                document.createElement(
                    "option"
                );


            // Store customer ID

            option.value =
                customer.id;


            // Display customer name

            option.textContent =
                customer.name;


            customerSelect.appendChild(
                option
            );

        });

    })

    .catch(error => {

        console.log(
            "Error loading customer names:",
            error
        );

    });
}


// ===============================
// 8. CUSTOMER FEEDBACK
// ===============================

function submitFeedback() {

    // Get selected customer ID

    let customerId =
        document.getElementById(
            "feedbackCustomer"
        ).value;


    // Get selected customer name

    let customerSelect =
        document.getElementById(
            "feedbackCustomer"
        );


    let customerName =
        customerSelect.options[
            customerSelect.selectedIndex
        ].text;


    // Get rating

    let rating =
        document.getElementById(
            "feedbackRating"
        ).value;


    // Get message

    let message =
        document.getElementById(
            "feedbackMessage"
        ).value;


    // Check customer

    if (customerId === "") {

        alert(
            "Please select your name."
        );

        return;
    }


    // Check rating

    if (rating === "") {

        alert(
            "Please select a rating."
        );

        return;
    }


    // Check feedback

    if (message === "") {

        alert(
            "Please enter your feedback."
        );

        return;
    }


    // Create feedback object

    let feedback = {

        customerId:
            Number(customerId),

        rating:
            Number(rating),

        message:
            message

    };


    // Send feedback to Spring Boot

    fetch(
        API_BASE_URL + "/api/feedback",
        {

            method: "POST",

            headers: {

                "Content-Type":
                    "application/json"

            },

            body:
                JSON.stringify(feedback)

        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Feedback submission failed"
            );

        }

        return response.json();

    })

    .then(data => {

        document.getElementById(
            "feedbackStatus"
        ).innerHTML =
            "Thank you " +
            customerName +
            " for your feedback!";


        // Clear form

        document.getElementById(
            "feedbackCustomer"
        ).value = "";

        document.getElementById(
            "feedbackRating"
        ).value = "";

        document.getElementById(
            "feedbackMessage"
        ).value = "";

    })

    .catch(error => {

        console.log(
            "Feedback Error:",
            error
        );

        alert(
            "Failed to submit feedback."
        );

    });
}