window.addEventListener("load", function () {

  const token = this.sessionStorage.jwt;
  const noti = this.document.querySelector(".noti");
  const email = this.document.querySelector(".email");
  const userEmail = this.sessionStorage.getItem("email");

  if(token){
    fetch("http://localhost:8080/patients/listAllDentists", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => response.json())
  .then(() => {
    console.log("se encontro token");
      location.replace("/patient.html")
  }).catch(() => {
  })
  }

  if(userEmail){
    noti.classList.remove("invisible")
    email.innerHTML = userEmail;
  }

  window.onbeforeunload = function() {
    sessionStorage.removeItem("email")
  };



const emailInput = this.document.querySelector(".emailInput");
const passwordInput = this.document.querySelector(".passwordInput");
const loginButton = this.document.querySelector(".loginButton");
const invalidInput = this.document.querySelector(".invalidInput");
loginButton.addEventListener("click",login)

function vibrateInput(input) {        
    input.classList.add('vibrate');
    setTimeout(() => {
      input.classList.remove('vibrate');
    }, 300);
  }

function login(){
    sessionStorage.clear()
    if(emailInput.value.trim() == ''){
        emailInput.classList.add("is-danger")
        vibrateInput(emailInput)
    }else{
        emailInput.classList.remove("is-danger")
    }
    if(passwordInput.value.trim() == ''){
        passwordInput.classList.add("is-danger")
        vibrateInput(passwordInput)
    }else{
        passwordInput.classList.remove("is-danger")
    }
    if(emailInput.value.trim() != '' && passwordInput.value.trim() != ''){
        loginButton.classList.add("is-loading");
        
        const payload = {
            email: emailInput.value,
            password: passwordInput.value
        }

        fetch("http://localhost:8080/landing-page/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      }).then(response => response.json())
      .then((data) => {
        loginButton.classList.remove("is-loading");
        invalidInput.classList.add("invisible-element")
        emailInput.classList.remove("is-danger")
        passwordInput.classList.remove("is-danger")
        sessionStorage.setItem("jwt",data.token);
        sessionStorage.setItem("patient", JSON.stringify(data.patient));
        sessionStorage.setItem("role",data.role)
        
        if(data.role == "USER"){
        location.replace("/patient.html")
        }else{
          location.replace("/dentist.html")
        }
      })
      .catch((e) =>{
            loginButton.classList.remove("is-loading");
            invalidInput.classList.remove("invisible-element")
            emailInput.classList.add("is-danger")
            passwordInput.classList.add("is-danger")

      })
    }

}



















































})