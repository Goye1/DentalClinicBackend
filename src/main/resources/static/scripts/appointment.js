window.onload = function(){

  sessionStorage.setItem("redirected",false)

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
      .then((data) => {
          console.log("todo ok");
      }).catch((e) => {
          this.sessionStorage.clear();
          this.location.replace("/login.html")
      })



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
    fetch("http://localhost:8080/patients/listAllDentists", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`
      }
  })
      .then((response) => response.json())
      .then((dentist) => {
        dentist.forEach((dentist) => {
          let dentistOptionRow = `
          <option value="${dentist.id}">${dentist.name} ${dentist.surname}</option>
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
      let selectedOptionId;
      selectedDentist = selectDentist.value;
      selectedOption = selectDentist.options[selectDentist.selectedIndex];
      selectedOptionId = selectedOption.value;
  

      let url = "http://localhost:8080/patients/addAppointment";
      let dateToSend = formatDate(actualDate);

        let requestBody = {
          appointmentDate: dateToSend,
          patient_id: patientS.id,
          dentist_id: selectedOptionId,
          reason: selectedReason,
        };
      
        fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },
          body: JSON.stringify(requestBody),
        })
          .then((response) => {response.json()
          console.log("response" + response);})
          .then((data) => {
            console.log(data + "esta es la info devuelta");
            setTimeout(() => {
              submitButton.classList.add("is-success")
              submitButton.innerHTML = "Scheduled succesfully, redirecting."
              submitButton.classList.remove("is-loading");
          }, 1500);
          
          setTimeout(() => {
            sessionStorage.setItem("redirected","yes")
            location.replace("/patient.html")
            
        }, 4000);
          })
          .catch((error) => {
            console.log(error);
            submitButton.classList.remove("is-loading");
            submitButton.classList.add("is-danger")
            submitButton.innerHTML = "Error trying to schedule"
    
          });
        

 


    }
  });
}