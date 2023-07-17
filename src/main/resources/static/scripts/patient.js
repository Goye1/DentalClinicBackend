  
window.addEventListener("load", function () {
  
  if (!this.sessionStorage.jwt || !this.sessionStorage.patient || sessionStorage.role == "ADMIN") {
    location.replace('/login.html');
    sessionStorage.clear();
  }
  const patientS = JSON.parse(sessionStorage.getItem("patient"));
  const token = this.sessionStorage.jwt;


fetch("http://localhost:8080/patients/listAllDentists", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => response.json())
  .then(() => {
  }).catch(() => {
      this.sessionStorage.clear();
      this.location.replace("/login.html")
  })


  const navName = this.document.querySelector(".name");
  navName.innerHTML = patientS.name + " " + patientS.surname


  const signout = this.document.querySelector(".sign-out")
  signout.onclick = () => {
    this.sessionStorage.clear();
  this.location.replace("/login.html")
  }
  
  
  
  let findProffesionals = document.querySelector(".findProffesionals");
  let findProffesionalsContainter = document.querySelector(
    ".findProffesionalsContainter"
    );

    let informationContainer = document.querySelector(".info");
    const myAppointmentsContainer = document.querySelector(".myFutureAppointmentsContainer")
    
    let myAppointments = document.querySelector(".myAppointments");
    const scheduleAppointment = document.querySelector(".scheduleAppointment");

let logoHome = this.document.querySelector(".logoHome")
let homeButton = document.querySelector(".homeButton")
let pastButton = document.querySelector(".pastAppointments");
let futButton = document.querySelector(".futAppointments");
let noAppointments = document.querySelector(".noAppointments");
let addAppointment = document.querySelector(".addAppointment")
let appointmentList = document.querySelector(".appointmentList");
let list = [];
let pastList = [];

logoHome.onclick = () => homeButton.click();

// FUTURE APPOINTMENTS
function viewAppointments() {
  informationContainer.classList.add("invisible");
  findProffesionalsContainter.classList.add("invisible");
  myAppointmentsContainer.classList.remove("invisible");
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
  
  fetch("http://localhost:8080/patients/listAppointments?idCard=" + patientS.idCard, {
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
        method: "DELETE",
        headers: {
          "Authorization": `Bearer ${token}`
        }
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
  fetch("http://localhost:8080/patients/pastAppointments?idCard=" + patientS.idCard, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
})
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


function vibrateInput() {        
  searchInput.classList.add('vibrate');
  
  setTimeout(() => {
    searchInput.classList.remove('vibrate');
  }, 300);
}
const noAppointmentsPro = document.querySelector(".noAppointmentsPro");
const searchInput = document.querySelector(".searchInput");
const searchButton = document.querySelector(".searchButton");
const viewAllButton = document.querySelector(".viewAllButton");
const dentistList = document.querySelector(".dentistList");



// FIND DENTISTS
findProffesionals.addEventListener("click", () => {
  informationContainer.classList.add("invisible");
  myAppointmentsContainer.classList.add("invisible");
  findProffesionalsContainter.classList.remove("invisible");

  let list = [];
  noAppointmentsPro.innerHTML = "Find any dentist.";

  function searchDentists() {
    let searchQuery = searchInput.value;
    if (searchQuery != "") {
      searchButton.classList.add("is-loading");
      noAppointmentsPro.classList.add("invisible");
      fetch("http://localhost:8080/patients/searchDentist?info=" + searchQuery, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`
        }
    })
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
            searchButton.classList.remove("is-loading");
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
    fetch("http://localhost:8080/patients/listAllDentists", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`
      }
  })
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
          viewAllButton.classList.remove("is-loading");
          noAppointmentsPro.classList.remove("invisible");
          noAppointmentsPro.innerHTML = "No dentists were found";
        } else {
          noAppointmentsPro.classList.add("invisible");
        }
      }).catch((e) => {
        noAppointmentsPro.classList.remove("invisible");
        viewAllButton.classList.remove("is-loading");
        noAppointmentsPro.innerHTML = "Error trying to get the dentists"
      });
  });
});

homeButton.onclick = function(){
  informationContainer.classList.remove("invisible");
  findProffesionalsContainter.classList.add("invisible");
  myAppointmentsContainer.classList.add("invisible");
  scheduleAppointmentContainer.classList.add("invisible");
}

if(sessionStorage.getItem("redirected") == "yes"){
  console.log("has sido redirigido !");
  sessionStorage.setItem("redirected","no")
  myAppointments.click();
}else{
  console.log("no has sido redigiragod");
}

window.addEventListener("beforeunload", function() {
  sessionStorage.setItem("redirected","no");
});

scheduleAppointment.onclick = () => this.location.replace("/appointment.html")

})
