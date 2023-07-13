  
window.addEventListener("load", function () {
  
  if (!this.sessionStorage.jwt || !this.sessionStorage.patient || sessionStorage.role == "ADMIN") {
    location.replace('/login.html');
    sessionStorage.clear();
  }
  const patient = JSON.parse(sessionStorage.getItem("patient"));
  const token = this.sessionStorage.jwt;


fetch("http://localhost:8080/patients/listAllDentists", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => response.json())
  .then((data) => {
      console.log("todo ok");
  }).catch((e) => {
      this.sessionStorage.clear();
      this.location.replace("/login.html")
  })


  const navName = this.document.querySelector(".name");
  navName.innerHTML = patient.name + " " + patient.surname


  const signout = this.document.querySelector(".sign-out")
  signout.onclick = () => {
    this.sessionStorage.clear();
  this.location.replace("/login.html")
  }
let myAppointments = document.querySelector(".myAppointments");
let myAppointmentsContainer = document.querySelector(
  ".myFutureAppointmentsContainer"
);

let findProffesionals = document.querySelector(".findProffesionals");
let findProffesionalsContainter = document.querySelector(
  ".findProffesionalsContainter"
);

let scheduleAppointmentContainer = document.querySelector(
  ".scheduleAppointmentContainer"
);
let scheduleAppointment = document.querySelector(".scheduleAppointment");

let pastButton = document.querySelector(".pastAppointments");
let futButton = document.querySelector(".futAppointments");
let noAppointments = document.querySelector(".noAppointments");
let addAppointment = document.querySelector(".addAppointment")

let appointmentList = document.querySelector(".appointmentList");
let list = [];
let pastList = [];



// MY APPOINTMENTS BUTTON

// FUTURE APPOINTMENTS
function viewAppointments() {
  findProffesionalsContainter.classList.add("invisible");
  myAppointmentsContainer.classList.remove("invisible");
  scheduleAppointmentContainer.classList.add("invisible");
  appointmentList.innerHTML = "";
  noAppointments.innerHTML =
    `You dont have any upcoming appointments, click <strong class="addAppointment" style="cursor: pointer; text-decoration: none;">here</strong> to add one.`;
  const addAppointmentLink = document.querySelector(".addAppointment");

  addAppointmentLink.addEventListener("click", () => {
    scheduleAppointment.click();
  });
  
  findProffesionalsContainter.classList.add("invisible");
  myAppointmentsContainer.classList.remove("invisible");
  futButton.classList.add("is-active");
  pastButton.classList.remove("is-active");
  console.log(token);
  fetch("http://localhost:8080/patients/listAppointments?idCard=" + patient.idCard, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
.then((respuesta) => respuesta.json())
.then((datos) => {
    let list = "";
    for (let i = 0; i < datos.length; i++) {
        let appointment = `<tr value="${datos[i].id}">
            <td>${datos[i].patient.name} ${datos[i].patient.surname}</td>
            <td>${datos[i].dentist.name} ${datos[i].dentist.surname}</td>
            <td>${datos[i].dentist.licenseNumber}</td>
            <td>${datos[i].appointmentDate[0]}/${datos[i].appointmentDate[1]}/${datos[i].appointmentDate[2]},
                ${datos[i].appointmentDate[3]}:${datos[i].appointmentDate[4]}</td>
            <td>${datos[i].reason}</td>
            <td><a class="cancel-link">Cancel</a></td>
        </tr>`;
        list += appointment;
    }
    appointmentList.innerHTML = list;

    const cancelLinks = document.querySelectorAll(".cancel-link");
    cancelLinks.forEach((link) => {
        link.addEventListener("click", cancelAppointment);
    });

    sessionStorage.setItem("list", list);
    if (list.length == 0) {
        noAppointments.classList.remove("invisible");
    } else {
        noAppointments.classList.add("invisible");
    }
})
.catch((message) => {
  noAppointments.innerHTML = "Error trying to get the appointments"
    console.log("error" + message);
    noAppointments.classList.remove("invisible");
});

function cancelAppointment(event) {
    const appointmentId = event.target.parentNode.parentNode.getAttribute("value");
  
    fetch(`http://localhost:8080/patients/deleteAppointment?id=${appointmentId}`, {
        method: "DELETE"
    })
    .then(response => {
        if (response.ok) {
            event.target.parentNode.parentNode.remove();
            futButton.click();
        } else {
            console.log("Failed to delete appointment.");
        }
    })
    .catch(error => {
        console.log("Error: " + error);
    });
}


}
myAppointments.addEventListener("click", viewAppointments);
futButton.addEventListener("click", viewAppointments);

// PAST APPOINTMENTS
pastButton.addEventListener("click", (event) => {
  noAppointments.innerHTML = "You dont have any past appointments.";
  pastButton.classList.add("is-active");
  futButton.classList.remove("is-active");
  appointmentList.innerHTML = "";
  fetch("http://localhost:8080/patients/pastAppointments?idCard=" + patient.idCard)
    .then((response) => response.json())
    .then((datos) => {
      pastList = [];
      for (let i = 0; i < datos.length; i++) {
        let pastAppointmentRow = `<tr>
            <td>${datos[i].patient.name} ${datos[i].patient.surname}</td>
            <td>${datos[i].dentist.name} ${datos[i].dentist.surname}</td>
            <td>${datos[i].dentist.licenseNumber}</td>
            <td>${datos[i].localDateTime[0]}/${datos[i].localDateTime[1]}/${datos[i].localDateTime[2]},
             ${datos[i].localDateTime[3]}:${datos[i].localDateTime[4]}</td>
            <td>${datos[i].reason}</td>
          </tr>`;
        pastList = pastList += pastAppointmentRow;
        appointmentList.innerHTML = pastList;
      }
      if (pastList.length == 0) {
        noAppointments.classList.remove("invisible");
      } else {
        noAppointments.classList.add("invisible");
      }
    })
    .catch((message) => {
      noAppointments.innerHTML = "Error trying to get the appointments"
      noAppointments.classList.remove("invisible");
    });
});

const noAppointmentsPro = document.querySelector(".noAppointmentsPro");
const searchInput = document.querySelector(".searchInput");
const searchButton = document.querySelector(".searchButton");
const viewAllButton = document.querySelector(".viewAllButton");
const dentistList = document.querySelector(".dentistList");


function vibrateInput() {        
  searchInput.classList.add('vibrate');

  setTimeout(() => {
      searchInput.classList.remove('vibrate');
  }, 300);
}


// FIND DENTISTS
findProffesionals.addEventListener("click", () => {
  myAppointmentsContainer.classList.add("invisible");
  findProffesionalsContainter.classList.remove("invisible");
  scheduleAppointmentContainer.classList.add("invisible");
  let list = [];
  noAppointmentsPro.innerHTML = "Find any dentist.";

  function searchDentists() {
    let searchQuery = searchInput.value;
    if (searchQuery != "") {
      searchButton.classList.add("is-loading");
      noAppointmentsPro.classList.add("invisible");
      fetch("http://localhost:8080/patients/searchDentist?info=" + searchQuery)
        .then((respuesta) => respuesta.json())
        .then((dentist) => {
          list = [];
          dentistList.innerHTML = "";
          dentist.forEach((dentist) => {
            const dentistRow = `
                <tr>
                  <td>${dentist.name}</td>
                  <td>${dentist.surname}</td>
                  <td>${dentist.licenseNumber}</td>
                  <td><a>Details</a></td>
                </tr>
                `;
            list = list += dentistRow;
            searchButton.classList.remove("is-loading");
            dentistList.innerHTML = list;
          });
          if (list.length == 0) {
            noAppointmentsPro.classList.remove("invisible");
            noAppointmentsPro.innerHTML = "No dentists were found";
          } else {
            noAppointmentsPro.classList.add("invisible");
          }
        })
        .catch(() => {
          searchButton.classList.remove("is-loading");
          noAppointmentsPro.innerHTML = "Error trying to get the dentists";
          noAppointmentsPro.classList.remove("invisible");
        });
    }else{
      vibrateInput();
    }
  }
  searchButton.addEventListener("click", searchDentists);
  document.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
      searchButton.click();
    }
  });

  //VIEW ALL DENTIST
  viewAllButton.addEventListener("click", (e) => {
    noAppointments.innerHTML = "No dentist were found";
    dentistList.innerHTML = "";
    viewAllButton.classList.add("is-loading");
    fetch("http://localhost:8080/patients/listAllDentists")
      .then((response) => 
        response.json())
      .then((dentist) => {
        list = [];
        dentistList.innerHTML = "";
        dentist.forEach((dentist) => {
          let dentistRow = `
          <tr>
            <td>${dentist.name}</td>
            <td>${dentist.surname}</td>
            <td>${dentist.licenseNumber}</td>
            <td><a>Details</a></td>
          </tr>
          `;
          list += dentistRow;
          viewAllButton.classList.remove("is-loading");
          dentistList.innerHTML = list;
        });
        if (list.length == 0) {
          noAppointmentsPro.classList.remove("invisible");
          noAppointments.innerHTML = "No dentists were found";
        } else {
          noAppointmentsPro.classList.add("invisible");
        }
      }).catch(() => {
        viewAllButton.classList.remove("is-loading");
        noAppointmentsPro.innerHTML = "Error trying to get the dentists"
      });
  });
});

// SCHEDULE AN APPOINTMENT




let actualDate;
scheduleAppointment.addEventListener("click", (e) => {
  findProffesionalsContainter.classList.add("invisible");
  myAppointmentsContainer.classList.add("invisible");
  scheduleAppointmentContainer.classList.remove("invisible");
  let selectDentist = document.querySelector(".selectDentist");
  let emailInput = document.querySelector(".emailInput");
  let reasonInput = document.querySelector(".reasonInput");


  let selectedReason;
  let submitButton = document.querySelector(".submitButton");
  let invalidDentist = document.querySelector(".invalidDentist")

  let emailState = false;
  let reasonState = false;
  let dentistState = false;
  let dateState = false;
  submitButton.innerHTML = "Schedule"
  submitButton.classList.remove("is-success")
  submitButton.classList.remove("is-danger")
  submitButton.classList.add("is-info")
  selectDentist.addEventListener("click", (e) => {
 

    let dentistOptions = [];
    fetch("http://localhost:8080/patients/listAllDentists")
      .then((response) => response.json())
      .then((dentist) => {
        dentist.forEach((dentist) => {
          let dentistOptionRow = `
          <option value="${dentist.licenseNumber}">Dr/a ${dentist.name} ${dentist.surname}</option>
          `;
          dentistOptions += dentistOptionRow;
        });
        selectDentist.innerHTML = dentistOptions;
        if (dentistOptions.length == 0) {
          selectDentist.innerHTML = `
          <option>No dentist were found</option>
          `;
        }
      }).catch(() => {
        selectDentist.innerHTML = `
          <option>Error trying to get the dentists</option>
          `
      })
  });
  var disabledDates = ["2023-06-15", "2023-06-20", "2023-06-25"];
  var currentDate = new Date();


  var calendars = bulmaCalendar.attach('[type="date"]', {
    type: "datetime",
    color: "info",
    validateLabel: "Accept",
    showTodayButton: false,
    enableYearSwitch: false,
    disabledDates: disabledDates,
    disabledWeekDays: [6, 0],
    showClearButton: false,
  });


  for (var i = 0; i < calendars.length; i++) {
    calendars[i].on("select", (date) => {
      actualDate = date.data.value();
      validateDate();
    });
    calendars[i].on("clear", () => {
      actualDate = undefined; 
    })
  }
  var element = document.querySelector("#my-element");
  if (element) {
    element.bulmaCalendar.on("select", function (datepicker) {
    });
  }


  function validateEmail(email) {
    selectedEmail = email.value;
    if (new RegExp("[a-z0-9]+@[a-z]+.[a-z]{2,3}").test(emailInput.value)) {
      document.querySelector(".invalidEmail").classList.add("invisible");
      emailState = true;
    } else {
      document.querySelector(".invalidEmail").classList.remove("invisible");
      emailState = false;
    }
  }

  function valideReason(reason) {
    selectedReason = reason.value;
    if (selectedReason.trim() !== "") {
      document.querySelector(".invalidReason").classList.add("invisible");
      reasonState = true;
    } else {
      document.querySelector(".invalidReason").classList.remove("invisible");
      reasonState = false;
    }
  }

  function validateSelect(){
    var selectedValue = selectDentist.options[selectDentist.selectedIndex].value;
    if (selectedValue != "Select dentist" && selectedValue != "No dentist were found"){
      invalidDentist.classList.add("invisible");
      dentistState = true;
    }else{
      invalidDentist.classList.remove("invisible");
      dentistState = false;
    }
  }

  emailInput.onchange = function () {
    validateEmail(emailInput);
  };

  
  selectDentist.onblur = function(){
    validateSelect();
  }

  function validateDate(){
      if(actualDate != undefined){
        document.querySelector(".invalidDate").classList.add("invisible")
        dateState = true;
      }else{
        document.querySelector(".invalidDate").classList.remove("invisible")
        dateState = false;
      }
  }



  // FORMAT DATE--------------------------------
  function formatDate(dateString) {
    var parts = dateString.split(" ");
    var datePart = parts[0];
    var timePart = parts[1];

    var dateParts = datePart.split("/");
    var month = dateParts[0];
    var day = dateParts[1];
    var year = dateParts[2];

    var formattedDate = year + "-" + month + "-" + day + "T" + timePart + ":00";

    return formattedDate;
  }
  //--------------------------------
  //SUBMIT FORM-------------------------

  submitButton.addEventListener("click", (e) => {
    validateEmail(emailInput);
    valideReason(reasonInput);
    validateSelect();
    validateDate();
    

    if (emailState && reasonState && dentistState && dateState) {
      submitButton.classList.add("is-loading");
      let selectedOptionLicenseNumber;
      selectedDentist = selectDentist.value;
      selectedOption = selectDentist.options[selectDentist.selectedIndex];
      selectedOptionLicenseNumber = selectedOption.value;
      fetch(
        `http://localhost:8080/patients/searchDentist?info=${selectedOptionLicenseNumber}`
      )
        .then((response) => response.json())
        .then((element) => {
          sessionStorage.setItem("idToSend", element[0].id);
        })
        .catch(() => {
          submitButton.classList.remove("is-loading");
        });

      let url = "http://localhost:8080/patients/addAppointment";
      let dateToSend = formatDate(actualDate);

      let requestBody = {
        appointmentDate: dateToSend,
        patient_id: patient.id,
        dentist_id: sessionStorage.getItem("idToSend"),
        reason: selectedReason,
      };

      fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
      })
        .then((response) => response.json())
        .then((data) => {
          setTimeout(() => {
            submitButton.classList.add("is-success")
            submitButton.innerHTML = "Scheduled succesfully, redirecting."
            submitButton.classList.remove("is-loading");
        }, 1500);
        
        setTimeout(() => {
          myAppointments.click();
      }, 4000);
        })
        .catch((error) => {
          submitButton.classList.remove("is-loading");
          submitButton.classList.add("is-danger")
          submitButton.innerHTML = "Error trying to schedule"

        });
      sessionStorage.clear;
    }
  });
});
})
