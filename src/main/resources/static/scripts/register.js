window.addEventListener("load", function () {

  const token = this.sessionStorage.jwt;

  if(token){
    fetch("http://localhost:8080/patients/listAllDentists", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => response.json())
  .then(() => {
      location.replace("/patient.html")
  }).catch((e) => {

  })
  }


  this.sessionStorage.clear();


  const userState = {
    name: "",
    surname: "",
    email: "",
    password: "",
    repeatedPassword: "",
    idCard: "",
    street: "",
    streetNumber: "",
    city: "",
    state: "",
  };

  const errorState = {
    name: false,
    surname: false,
    email: false,
    password: false,
    repeatedPassword: false,
    idCard: false,
    street: false,
    streetNumber: false,
    city: false,
    state: false,
  };

  const nameInput = document.querySelector(".nameInput");
  const surnameInput = document.querySelector(".surnameInput");
  const emailInput = document.querySelector(".emailInput");
  const passwordInput = document.querySelector(".passwordInput");
  const repeatPasswordInput = document.querySelector(".repeatPasswordInput");
  const idCardInput = document.querySelector(".idCardInput");
  const streetInput = document.querySelector(".streetInput");
  const streetNmbInput = document.querySelector(".streetNmbInput");
  const cityInput = document.querySelector(".cityInput");
  const stateInput = document.querySelector(".stateInput");

  const invalidName = document.querySelector(".invalidName");
  const invalidSurname = document.querySelector(".invalidSurname");
  const invalidEmail = document.querySelector(".invalidEmail");
  const invalidPass = document.querySelector(".invalidPass");
  const invalidPassRep = document.querySelector(".invalidPassRep");
  const invalidIdCard = document.querySelector(".invalidIdCard");
  const invalidStreet = document.querySelector(".invalidStreet");
  const invalidStreetNmb = document.querySelector(".invalidStreetNmb");
  const invalidCity = document.querySelector(".invalidCity");
  const invalidState = document.querySelector(".invalidState");

  function showErrors() {
    errorState.name
      ? invalidName.classList.remove("invisible-element")
      : invalidName.classList.add("invisible-element");

    errorState.surname
      ? invalidSurname.classList.remove("invisible-element")
      : invalidSurname.classList.add("invisible-element");

    errorState.email
      ? invalidEmail.classList.remove("invisible-element")
      : invalidEmail.classList.add("invisible-element");

    errorState.password
      ? invalidPass.classList.remove("invisible-element")
      : invalidPass.classList.add("invisible-element");

    errorState.repeatedPassword
      ? invalidPassRep.classList.remove("invisible-element")
      : invalidPassRep.classList.add("invisible-element");

    errorState.idCard
      ? invalidIdCard.classList.remove("invisible-element")
      : invalidIdCard.classList.add("invisible-element");

    errorState.street
      ? invalidStreet.classList.remove("invisible-element")
      : invalidStreet.classList.add("invisible-element");

    errorState.streetNumber
      ? invalidStreetNmb.classList.remove("invisible-element")
      : invalidStreetNmb.classList.add("invisible-element");

    errorState.city
      ? invalidCity.classList.remove("invisible-element")
      : invalidCity.classList.add("invisible-element");

    errorState.state
      ? invalidState.classList.remove("invisible-element")
      : invalidState.classList.add("invisible-element");
  }

  function validateName(nombre) {
    return !nombre.includes(" ") && !/[0-9]/.test(nombre);
  }

  function validateEmail(email) {
    const regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
    return regex.test(email);
  }

  function comparePass(pass1, pass2) {
    return pass1 === pass2;
  }

  function isEmpty(string) {
    return string.trim() !== "";
  }

  function listener() {
    userState.name = nameInput.value.trim();
    userState.surname = surnameInput.value.trim();
    userState.email = emailInput.value.trim();
    userState.password = passwordInput.value.trim();
    userState.repeatedPassword = repeatPasswordInput.value.trim();
    userState.idCard = idCardInput.value.trim();
    userState.street = streetInput.value.trim();
    userState.streetNumber = streetNmbInput.value.trim();
    userState.city = cityInput.value.trim();
    userState.state = stateInput.value.trim();

    errorState.name = !validateName(userState.name) || !isEmpty(userState.name);
    errorState.surname =
      !validateName(userState.surname) || !isEmpty(userState.surname);
    errorState.email = !isEmpty(userState.email);
    errorState.repeatedPassword = !isEmpty(userState.repeatedPassword);
    errorState.password = !isEmpty(userState.password);
    errorState.idCard = !isEmpty(userState.idCard);
    errorState.street = !isEmpty(userState.street);
    errorState.streetNumber = !isEmpty(userState.streetNumber);
    errorState.city = !isEmpty(userState.city);
    errorState.state = !isEmpty(userState.state);

    if (errorState.repeatedPassword == false && errorState.password == false) {
      if (!comparePass(userState.repeatedPassword, userState.password)) {
        errorState.repeatedPassword = true;
        errorState.password = true;
        invalidPass.innerHTML = "Passwords must match";
        invalidPassRep.innerHTML = "Passwords must match";
      }
    } else {
      invalidPass.innerHTML = "This field is obligatory";
      invalidPassRep.innerHTML = "This field is obligatory";
    }

    if (errorState.email == false) {
      if (!validateEmail(userState.email)) {
        errorState.email = true;
        invalidEmail.innerHTML = "Invalid email";
      }
    } else {
      invalidEmail.innerHTML = "This field is obligatory";
    }

    showErrors();
  }

  let ins = document.querySelectorAll(".ins");
  ins.forEach((ins) => {
    ins.addEventListener("keydown", function (event) {
      const key = event.key;
      const regex = /^[A-Za-z\s]+$/;
      if (key.length === 1 && !regex.test(key)) {
        event.preventDefault();
      }
    });
  });

  const registerButton = this.document.querySelector(".registerButton");
  registerButton.onclick = function () {
  
    listener();
    if (Object.values(errorState).every((value) => value !== true)) {
      
      registerButton.classList.remove("is-danger");

      registerButton.classList.remove("is-success");
      registerButton.classList.add("is-loading");
      const payload = {
        firstname: nameInput.value,
        lastname: surnameInput.value,
        email: emailInput.value,
        password: passwordInput.value,
        address: {
          street: streetInput.value,
          number: streetNmbInput.value,
          city: cityInput.value,
          state: stateInput.value,
        },
        idCard: idCardInput.value,
      };

      fetch("http://localhost:8080/landing-page/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      })
        .then((response) => {
          registerButton.classList.remove("is-danger");
          if (!response.ok) {
            registerButton.classList.remove("is-loading");
            return response.text().then((errorMessage) => {
              if (
                errorMessage == "A user is already registered with that email."
              ) {
                invalidEmail.innerHTML = "Email already in use.";
                invalidEmail.classList.remove("invisible-element");
              }
              if (
                errorMessage ==
                `A patient with the id card: ${payload.idCard} already exists`
              ) {
                invalidIdCard.innerHTML =
                  "Patient with that id card already exists.";
                invalidIdCard.classList.remove("invisible-element");
              }
            });
          }
          return response.json();
        })
        .then((data) => {

          registerButton.classList.add("is-success");
          sessionStorage.setItem("email",payload.email)
          setTimeout(() => location.replace("/login.html"),2200)
        })
        .catch(() => {
          registerButton.classList.remove("is-loading");
          registerButton.innerHTML = "Server error";
          registerButton.classList.add("is-danger");
        });
    }
  };
});
