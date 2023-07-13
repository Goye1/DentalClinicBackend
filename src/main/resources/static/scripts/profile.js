window.addEventListener("load", function () {
    
    
    const patient = JSON.parse(sessionStorage.getItem("patient"));
    const token = this.sessionStorage.getItem("jwt");
  

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

        
        let newPatient = {
            id: 1,
            name: patient.name,
            surname: patient.surname,
            idCard: patient.idCard,
            dischargeDate: null,
            email: patient.email,
            address: {
                id: patient.address.id,
                street: patient.address.street,
                number: patient.address.number,
                city: patient.address.city,
                state: patient.address.state
            }
        }


        const nameInput = this.document.querySelector(".nameInput");
        const surnameInput = this.document.querySelector(".surnameInput")
        const idCardInput = this.document.querySelector(".idCardInput");
        const streetInput = this.document.querySelector(".streetInput");
        const streetNmbInput = this.document.querySelector(".streetNmbInput");
        const cityInput = this.document.querySelector(".cityInput");
        const stateInput = this.document.querySelector(".stateInput");
        const saveButton = this.document.querySelector(".save");

        nameInput.value = patient.name;
        surnameInput.value = patient.surname;
        idCardInput.value = patient.idCard;
        streetInput.value = patient.address.street;
        streetNmbInput.value = patient.address.number;
        cityInput.value = patient.address.city;
        stateInput.value = patient.address.state;

        saveButton.onclick = () => {

            const payload = {
                id: patient.id,
                name: nameInput.value,
                surname: surnameInput.value,
                idCard: idCardInput.value,
                dischargeDate: patient.dischargeDate,
                address: {
                    street: streetInput.value,
                    number: streetNmbInput.value,
                    city: cityInput.value,
                    state: stateInput.value
                }
            }
            fetch("http://localhost:8080/patients/update", {
                method: "PUT",
                headers: {
                  "Content-Type": "application/json",
                  "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(payload),
              }).then((response) => response.json())
                .then((data) => {
                    let newPatient = {
                        id: patient.id,
                        name: nameInput.value,
                        surname: surnameInput.value,
                        idCard: idCardInput.value,
                        dischargeDate: patient.dischargeDate,
                        email: patient.email,
                        address: {
                            id: patient.address.id,
                            street:  streetInput.value,
                            number: streetNmbInput.value,
                            city: cityInput.value,
                            state: stateInput.value
                        }
                    }
                    console.log(data);
                    sessionStorage.removeItem("patient")
                    sessionStorage.setItem("patient",JSON.stringify(newPatient))
                    this.location.reload();
              }).catch(e => {
                console.log(e);
              })


        }
















})