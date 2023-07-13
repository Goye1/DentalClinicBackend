if (!this.sessionStorage.jwt || this.sessionStorage.role == "USER" ) {
  location.replace('/login.html');
}

window.addEventListener("load", function () {
const patientsButton = document.querySelector(".patientsButton");
const dentistsButton = document.querySelector(".dentistsButton");
const appointmentsButton = document.querySelector(".appointmentsButton");
const addDentistButton = document.querySelector(".addDentistButton");


const patientContainer = document.querySelector(".patientContainer");
const dentistContainer = document.querySelector(".dentistContainer");
const appointmentsContainer = document.querySelector(".appointmentsContainer");
const addDentistContainer = document.querySelector(".addDentistContainer");

const makeInvisible = (e) => {
    e.classList.remove("invisible");
}

patientsButton.onclick = function(){
  patientContainer.classList.remove("invisible");
  appointmentsContainer.classList.add("invisible")
  dentistContainer.classList.add("invisible");
  addDentistContainer.classList.add("invisible")
    const searchButton = document.querySelector(".searchButton");
    const viewAllButton  = document.querySelector(".viewAllButton");
    const searchInput = document.querySelector(".searchInput");
    const patientList = document.querySelector(".patientList");
    const patientResponse = document.querySelector(".patientResponse");
    let list = [];


    function vibrateInput() {        
        searchInput.classList.add('vibrate');
    
        setTimeout(() => {
            searchInput.classList.remove('vibrate');
        }, 300);
      }

//SEARCH PATIENTS

    function searchPatients() {
      let viewLinksP = [];
      let searchQuery = searchInput.value;
      if (searchQuery != "") {
          searchButton.classList.add("is-loading")
            patientList.innerHTML = "";
            patientResponse.classList.add("invisible");
          fetch("http://localhost:8080/admin/searchPatient?info=" + searchQuery)
            .then((response) => response.json())
            .then((patient) => {
              list = [];
              patient.forEach((patient) => {
                let date = new Date(patient.dischargeDate);
                let formatDate = date.toLocaleDateString('es-ES', { year: 'numeric', month: 'numeric', day: 'numeric' }).replace(/\//g, '/');
                let app;

                if(patient.discharged == false){
                  state = "ADMITTED"
                  app= "View Appointments"
             }else{
                  state = "DISCHARGED"
                  app = "";
             }

                const patientRow = `
                    <tr value="${patient.idCard}">
                      <td>${patient.name}</td>
                      <td>${patient.surname}</td>
                      <td>${patient.idCard}</td>
                      <td>${formatDate}</td>
                      <td class="${state.toLowerCase()}">${state}</td>
                      <td><a class="view-linkP">${app}</a></td>
                    </tr>
                    `;
                list = list += patientRow;
                searchButton.classList.remove("is-loading")
                patientList.innerHTML = list;
              });
              if (list.length == 0) {
                searchButton.classList.remove("is-loading")
                patientResponse.classList.remove("invisible");
                patientResponse.innerHTML = "No patients were found";
              } else {
                patientResponse.classList.add("invisible");
                 viewLinksP = document.querySelectorAll(".view-linkP");

                viewLinksP.forEach((link) => {
                  link.addEventListener("click",viewAppointments);
                })
              }
            })
            .catch((e) => {
              searchButton.classList.remove("is-loading")
                patientResponse.innerHTML = "Error trying to get the patients";
                patientResponse.classList.remove("invisible");
            });
        }else{
            vibrateInput();
        }
        
      }

      searchButton.addEventListener("click", searchPatients);
      document.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
          searchButton.click();
        }
      });


//VIEW ALL PATIENTS 


viewAllButton.addEventListener("click",() => {
 let viewLinksP = []
    let searchQuery = searchInput.value;
    viewAllButton.classList.add("is-loading");
    patientList.innerHTML = "";
    fetch("http://localhost:8080/admin/listAllPatients")
      .then((response) =>
       response.json())
      .then((patient) => {
        list = [];
        patientList.innerHTML = "";
        patient.forEach((patient) => {
            let date = new Date(patient.dischargeDate);
            let formatDate = date.toLocaleDateString('es-ES', { year: 'numeric', month: 'numeric', day: 'numeric' }).replace(/\//g, '/');
            let state;
            let app;
            if(patient.discharged == false){
                 state = "ADMITTED"
                 app = "View appointments"
            }else{
                 state = "DISCHARGED"
                 app = "";
            }
          let patientRow = `
          <tr value="${patient.idCard}">
            <td>${patient.name}</td>
            <td>${patient.surname}</td>
            <td>${patient.idCard}</td>
            <td>${formatDate}</td>
            <td class="${state.toLowerCase()}">${state}</td>
            <td><a class="view-linkP">${app}</a></td>
          </tr>
          `;
          list += patientRow;
          viewAllButton.classList.remove("is-loading");
          patientList.innerHTML = list;
        });
        if (list.length == 0) {
            patientResponse.classList.remove("invisible");
            patientResponse.innerHTML = "No patients were found";
        } else {
           viewLinksP = document.querySelectorAll(".view-linkP");
          viewLinksP.forEach((link) => {
            link.addEventListener("click",viewAppointments);
          })
            patientResponse.classList.add("invisible");
        }
      }).catch(() => {
        viewAllButton.classList.remove("is-loading");
        patientResponse.classList.remove("invisible")
        patientResponse.innerHTML = "Error trying to get the patietns"
      });
    });
  }
  
  
//DENTISTS

dentistsButton.onclick = function(){
  appointmentsContainer.classList.add("invisible")
  patientContainer.classList.add("invisible");
  dentistContainer.classList.remove("invisible");
  addDentistContainer.classList.add("invisible")

  const searchButtonDentist = document.querySelector(".searchButtonDentist");
  const viewAllButtonDentist =  document.querySelector(".viewAllButtonDentist");
  const dentistResponse = document.querySelector(".dentistResponse");
  const searchInputDentists = document.querySelector(".searchInputDentists");
  const dentistList = document.querySelector(".dentistList");

  function vibrateInput() {        
    searchInputDentists.classList.add('vibrate');

    setTimeout(() => {
      searchInputDentists.classList.remove('vibrate');
    }, 300);
  }
  function searchDentists() {
   let viewLinks = [];
    let searchQuery = searchInputDentists.value;
    if (searchQuery != "") {
      searchButtonDentist.classList.add("is-loading")
        dentistList.innerHTML = "";
        dentistResponse.classList.add("invisible");
      fetch("http://localhost:8080/admin/searchDentist?info=" + searchQuery)
        .then((response) => response.json())
        .then((dentist) => {
          list = [];
          dentist.forEach((dentist) => {
    
            const dentistRow = `
                <tr value="${dentist.licenseNumber}">
                  <td>${dentist.name}</td>
                  <td>${dentist.surname}</td>
                  <td>${dentist.licenseNumber}</td>
                  <td><a class="view-link">View Appointments</a></td>
                </tr>
                `;
            list = list += dentistRow;
            searchButtonDentist.classList.remove("is-loading")
            dentistList.innerHTML = list;
          });
          if (list.length == 0) {
            searchButtonDentist.classList.remove("is-loading")
            dentistResponse.classList.remove("invisible");
            dentistResponse.innerHTML = "No dentists were found";
          } else {
             viewLinks = document.querySelectorAll(".view-link");
            viewLinks.forEach((link) => {
              link.addEventListener("click",viewDentistAppointments);
            })
            dentistResponse.classList.add("invisible");
          }
        })
        .catch((e) => {
          searchButtonDentist.classList.remove("is-loading")
          dentistResponse.innerHTML = "Error trying to get the dentists";
          dentistResponse.classList.remove("invisible");
        });
    }else{
        vibrateInput();
    }
    
  }

  searchButtonDentist.addEventListener("click", searchDentists);
  document.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
      searchButtonDentist.click();
    }
  });


  function viewAllDentists(){
    let viewLinks = [];
    dentistList.innerHTML = "";
    viewAllButtonDentist.classList.add("is-loading")
    fetch("http://localhost:8080/admin/listAllDentists")
      .then((response) => 
        response.json())
      .then((dentist) => {
        list = [];
        dentistList.innerHTML = "";
        dentist.forEach((dentist) => {
          let dentistRow = `
          <tr value=${dentist.licenseNumber}>
            <td>${dentist.name}</td>
            <td>${dentist.surname}</td>
            <td>${dentist.licenseNumber}</td>
            <td><a class="view-link">View appointments</a></td>
          </tr>
          `;
          list += dentistRow;
          viewAllButtonDentist.classList.remove("is-loading")
          dentistList.innerHTML = list;
        });
  

        if (list.length == 0) {
          dentistResponse.classList.remove("invisible");
          dentistResponse.innerHTML = "No dentists were found";
        } else {
           viewLinks = document.querySelectorAll(".view-link");
            viewLinks.forEach((link) => {
              link.addEventListener("click",viewDentistAppointments);
            })
          dentistResponse.classList.add("invisible");
        }
      }).catch(() => {
        dentistResponse.classList.remove("invisible");
        viewAllButtonDentist.classList.remove("is-loading")
        dentistResponse.innerHTML = "Error trying to get the dentists"
      });
  };

  viewAllButtonDentist.addEventListener("click",viewAllDentists)
}

let patientIdCard;
let futAppointments = document.querySelector(".futAppointments");
let pastAppointments = document.querySelector(".pastAppointments")
let appointmentList = document.querySelector(".appointmentList");
let appointmentsResponse = document.querySelector(".appointmentsResponse");
let variableNode = document.querySelector(".variable-node");

function viewAppointments(event){

  appointmentsContainer.classList.remove("invisible")
  variableNode.innerHTML = "License number"
  appointmentList.innerHTML = "";
  patientContainer.classList.add("invisible");
  dentistContainer.classList.add("invisible");
  addDentistContainer.classList.add("invisible")
  
  patientIdCard = event.target.parentNode.parentNode.getAttribute("value");
  futAppointments.addEventListener("click",viewFutAppointments)
  viewFutAppointments();
  
  function viewFutAppointments(){
    patientIdCard = event.target.parentNode.parentNode.getAttribute("value");
    variableNode.innerHTML = "License number"
    appointmentsResponse.classList.add("invisible")
      futAppointments.classList.add("is-active");
      pastAppointments.classList.remove("is-active")
      appointmentList.innerHTML = "";
    fetch(`http://localhost:8080/admin/listPatientAppointments?idCard=${patientIdCard}`)
    .then((response) => response.json())
    .then((appointment) => {
      list = [];
          appointment.forEach(appointment => {
            let appointmentRow = `
            <tr>
            <td>${appointment.patient.name} ${appointment.patient.surname}</td>
            <td>${appointment.dentist.name} ${appointment.dentist.surname}</td>
            <td>${appointment.dentist.licenseNumber}</td>
            <td>${appointment.appointmentDate[0]}/${appointment.appointmentDate[1]}/${appointment.appointmentDate[2]},
             ${appointment.appointmentDate[3]}:${appointment.appointmentDate[4]}</td>
            <td>${appointment.reason}</td>
            </tr>
            `
            list += appointmentRow;
            appointmentList.innerHTML = list;
          })
          if(list.length == 0){
            appointmentsResponse.classList.remove("invisible")
            appointmentsResponse.innerHTML = "No future appointments found"
          }else{
            appointmentsResponse.classList.add("invisible")
          }
    }).catch(() => {
      appointmentsResponse.classList.remove("invisible")
      appointmentsResponse.innerHTML = "Error trying to get future appointments"
    })
  }

    pastAppointments.onclick = function(){
      appointmentsResponse.classList.add("invisible")
      variableNode.innerHTML = "License number"
      futAppointments.classList.remove("is-active");
      pastAppointments.classList.add("is-active")
      appointmentList.innerHTML = "";
        fetch(`http://localhost:8080/admin/pastAppointmentsPatient?idCard=${patientIdCard}`)
        .then((response) => response.json())
        .then((appointment) => {
          list = [];
              appointment.forEach(appointment => {
                let appointmentRow = `
                <tr>
                <td>${appointment.patient.name} ${appointment.patient.surname}</td>
                <td>${appointment.dentist.name} ${appointment.dentist.surname}</td>
                <td>${appointment.dentist.licenseNumber}</td>
                <td>${appointment.localDateTime[0]}/${appointment.localDateTime[1]}/${appointment.localDateTime[2]},
                 ${appointment.localDateTime[3]}:${appointment.localDateTime[4]}</td>
                <td>${appointment.reason}</td>
                </tr>
                `
                list += appointmentRow;
                appointmentList.innerHTML = list;
              })
                if(list.length == 0){
                  appointmentsResponse.classList.remove("invisible")
                  appointmentsResponse.innerHTML = "No past appointments found"
                }else{
                  appointmentsResponse.classList.add("invisible")
                }
              }).catch(() => {
            appointmentsResponse.classList.remove("invisible")
            appointmentsResponse.innerHTML = "Error trying to get past appointments"
      })
}
}


let dentistLicenseNumber;
function viewDentistAppointments(event){
  patientContainer.classList.add("invisible");
  dentistContainer.classList.add("invisible");
  addDentistContainer.classList.add("invisible")
  variableNode.innerHTML = "Patient Id card"
  dentistLicenseNumber = event.target.parentNode.parentNode.getAttribute("value");
  appointmentsContainer.classList.remove("invisible")
  
  appointmentList.innerHTML = "";
  viewDentistFutAppointments();
  futAppointments.addEventListener("click",viewDentistFutAppointments)
  
  function viewDentistFutAppointments(){
    appointmentsResponse.classList.add("invisible")
    variableNode.innerHTML = "Patient Id card"
    appointmentList.innerHTML = "";
      futAppointments.classList.add("is-active");
      pastAppointments.classList.remove("is-active")
      fetch(`http://localhost:8080/admin/listDentistAppointments?licenseNumber=${dentistLicenseNumber}`)
      .then((response) => response.json())
      .then((appointment) => {
        list = [];
        appointment.forEach(appointment => {
          let appointmentRow = `
          <tr>
          <td>${appointment.patient.name} ${appointment.patient.surname}</td>
          <td>${appointment.dentist.name} ${appointment.dentist.surname}</td>
          <td>${appointment.patient.idCard}</td>
          <td>${appointment.appointmentDate[0]}/${appointment.appointmentDate[1]}/${appointment.appointmentDate[2]},
           ${appointment.appointmentDate[3]}:${appointment.appointmentDate[4]}</td>
          <td>${appointment.reason}</td>
          </tr>
          `
          list += appointmentRow;
          appointmentList.innerHTML = list;
        })
        if(list.length == 0){
          appointmentsResponse.innerHTML = `No future appointments were found`;
          appointmentsResponse.classList.remove("invisible")
        }else{
          appointmentsResponse.classList.add("invisible")
        }
      }).catch(() => {
        appointmentsResponse.classList.remove("invisible")
        appointmentsResponse.innerHTML = `Error trying to get future appointments`;
      })
  }

  pastAppointments.onclick = () => {
    variableNode.innerHTML = "Patient Id card"
      appointmentList.innerHTML = "";
      futAppointments.classList.remove("is-active");
      pastAppointments.classList.add("is-active")

      fetch(`http://localhost:8080/admin/pastAppointmentsDentist?licenseNumber=${dentistLicenseNumber}`)
      .then((response) => response.json())
      .then((appointment) => {
        list = [];
        appointment.forEach(appointment => {
          let appointmentRow = `
          <tr>
          <td>${appointment.patient.name} ${appointment.patient.surname}</td>
          <td>${appointment.dentist.name} ${appointment.dentist.surname}</td>
          <td>${appointment.patient.idCard}</td>
          <td>${appointment.localDateTime[0]}/${appointment.localDateTime[1]}/${appointment.localDateTime[2]},
           ${appointment.localDateTime[3]}:${appointment.localDateTime[4]}</td>
          <td>${appointment.reason}</td>
          </tr>
          `
          list += appointmentRow;
          appointmentList.innerHTML = list;
        })
        if(list.length == 0){
          appointmentsResponse.innerHTML = `No past appointments were found`;
          appointmentsResponse.classList.remove("invisible")
        }else{
          appointmentsResponse.classList.add("invisible")
        }
      }).catch((e) => {
        appointmentsResponse.classList.remove("invisible")
        appointmentsResponse.innerHTML = `Error trying to get past appointments`;
      })
  }
}

addDentistButton.addEventListener("click", () => {
  patientContainer.classList.add("invisible");
  dentistContainer.classList.add("invisible");
  addDentistContainer.classList.remove("invisible");

    let nameInput = document.querySelector(".nameInput");
    let surnameInput = document.querySelector(".surnameInput");
    let licenseInput = document.querySelector(".licenseInput");
    const submitButton = document.querySelector(".submitButton");
    const invalidName = document.querySelector(".invalidName");
    const invalidSurname = document.querySelector(".invalidSurname");
    const invalidLicense = document.querySelector(".invalidLicense");
    const alreadyExists = document.querySelector(".alreadyExists");

    nameInput.value = "";
    surnameInput.value = "";
    licenseInput.value = "";

    let ins = document.querySelectorAll(".ins");
    ins.forEach(ins => {
      ins.addEventListener('keydown', function(event) {
        const key = event.key;
        const regex = /^[A-Za-z]+$/;
        if (key.length === 1 && !regex.test(key)) {
          event.preventDefault();
        }
      });
    })

    let name = false;
    let surname = false;
    let license = false;
    submitButton.onclick = (e) => {
      if(nameInput.value == ''){
        name = false;
        invalidName.classList.remove("invisible");
      }else{
        name = true;
        invalidName.classList.add("invisible");
      }
      if(surnameInput.value == ''){
        surname = false;
        invalidSurname.classList.remove("invisible");
      }else{
        surname = true;
        invalidSurname.classList.add("invisible");
      }
      if(licenseInput.value  == ''){
        license = false;
        invalidLicense.classList.remove("invisible")
      }else{
        license = true;
        invalidLicense.classList.add("invisible")
      }
      let requestBody = {
        name: nameInput.value,
        surname: surnameInput.value,
        licenseNumber: licenseInput.value
      };
      if(name && surname && license){
        fetch("http://localhost:8080/admin/add", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestBody)
        }).then((response) => {
          if (response.ok) {
            submitButton.innerHTML = "Dentist registered, redirecting."
            setTimeout(() => {
              dentistsButton.click();
            },3000)
            alreadyExists.classList.add("invisible")
            return response.json();
          } else {
            alreadyExists.classList.remove("invisible")
          }})
        .then((data) => {
        }).catch((e) => {
        })
      
      }
    }
})
})