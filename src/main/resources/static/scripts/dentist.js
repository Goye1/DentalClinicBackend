const patientsButton = document.querySelector(".patientsButton");
const dentistsButton = document.querySelector(".dentistsButton");
const appointmentsButton = document.querySelector(".appointmentsButton");

const patientContainer = document.querySelector(".patientContainer");
const dentistContainer = document.querySelector(".dentistContainer");
const appointmentsContainer = document.querySelector(".appointmentsContainer");

patientsButton.onclick = function(){
  appointmentsContainer.classList.add("invisible")
    patientContainer.classList.remove("invisible");
    dentistContainer.classList.add("invisible");
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
      let searchQuery = searchInput.value;
      if (searchQuery != "") {
          searchButton.classList.add("is-loading")
            patientList.innerHTML = "";
            patientResponse.classList.add("invisible");
          fetch("http://localhost:8080/patients/search?info=" + searchQuery)
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
                      <td><a class="view-link">${app}</a></td>
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
                let viewLinks = document.querySelectorAll(".view-link");

                viewLinks.forEach((link) => {
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
    let searchQuery = searchInput.value;
    viewAllButton.classList.add("is-loading");

    patientList.innerHTML = "";
    fetch("http://localhost:8080/patients/listAll")
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
            <td><a class="view-link">${app}</a></td>
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
          let viewLinks = document.querySelectorAll(".view-link");
          viewLinks.forEach((link) => {
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
    let searchQuery = searchInputDentists.value;
    if (searchQuery != "") {
      searchButtonDentist.classList.add("is-loading")
        dentistList.innerHTML = "";
        dentistResponse.classList.add("invisible");
      fetch("http://localhost:8080/dentists/search?info=" + searchQuery)
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
            let viewLinks = document.querySelectorAll(".view-link");
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


  viewAllButtonDentist.addEventListener("click", () => {
    dentistList.innerHTML = "";
    viewAllButtonDentist.classList.add("is-loading")
    fetch("http://localhost:8080/dentists/listAll")
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
            <td><a>View appointments</a></td>
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
          dentistResponse.classList.add("invisible");
        }
      }).catch(() => {
        viewAllButtonDentist.classList.remove("is-loading")
        dentistResponse.innerHTML = "Error trying to get the dentists"
      });
  });
}



let patientIdCard;



function viewAppointments(event){
  let futAppointments = document.querySelector(".futAppointments");
  let pastAppointments = document.querySelector(".pastAppointments")
  let appointmentList = document.querySelector(".appointmentList");
  let appointmentsResponse = document.querySelector(".appointmentsResponse");
  appointmentsContainer.classList.remove("invisible")
  

  appointmentList.innerHTML = "";

  patientContainer.classList.add("invisible");
  dentistContainer.classList.add("invisible");
  
   patientIdCard = event.target.parentNode.parentNode.getAttribute("value");
  viewFutAppointments();
  futAppointments.addEventListener("click",viewFutAppointments)
  
  function viewFutAppointments(){
      futAppointments.classList.add("is-active");
      pastAppointments.classList.remove("is-active")
      appointmentList.innerHTML = "";
    fetch(`http://localhost:8080/patients/listAppointments?idCard=${patientIdCard}`)
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
      futAppointments.classList.remove("is-active");
      pastAppointments.classList.add("is-active")
      appointmentList.innerHTML = "";
        fetch(`http://localhost:8080/patients/pastAppointments?idCard=${patientIdCard}`)
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
appointmentsButton.addEventListener("click", viewAppointments);
}


let dentistLicenseNumber;
function viewDentistAppointments(event){
  patientContainer.classList.add("invisible");
  dentistContainer.classList.add("invisible");
  let futAppointments = document.querySelector(".futAppointments");
  let pastAppointments = document.querySelector(".pastAppointments")
  let appointmentList = document.querySelector(".appointmentList");
  let appointmentsResponse = document.querySelector(".appointmentsResponse");
  appointmentsContainer.classList.remove("invisible")

  appointmentList.innerHTML = "";


   dentistLicenseNumber = event.target.parentNode.parentNode.getAttribute("value");
   viewDentistFutAppointments();
  futAppointments.addEventListener("click",viewDentistFutAppointments)

  function viewDentistFutAppointments(){
    appointmentList.innerHTML = "";
      futAppointments.classList.add("is-active");
      pastAppointments.classList.remove("is-active")
      



  }

}