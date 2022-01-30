import React from "react";
import SemanticDatepicker from "react-semantic-ui-datepickers";
import { Button, Input, Modal } from "semantic-ui-react";
import api from "../config/AxiosConfig";

const CustomerUpdate = () => {
  const [identity, setIdentity] = React.useState("");
  const [fullName, setFullName] = React.useState("");
  const [phone, setPhoneNumber] = React.useState("");
  const [income, setIncome] = React.useState(0);
  const [birthDate, setBirthDate] = React.useState("");
  const [open, setOpen] = React.useState(false);

  const splitDate = (data) => {
    var res = data.split("-");
    return res[2] + "-" + res[1] + "-" + res[0];
  };

  const handleIdentityChange = (e) => {
    setIdentity(e.currentTarget.value);
  };

  const handleFullNameChange = (e) => {
    setFullName(e.currentTarget.value);
  };

  const handlePhoneNumberChange = (e) => {
    setPhoneNumber(e.currentTarget.value);
  };

  const handleIncomeChange = (e) => {
    setIncome(e.target.value);
  };

  const handleBirthDateChange = (event, data) => {
    setBirthDate(
      splitDate(data.value.toLocaleDateString().replaceAll(".", "-"))
    );
  };

  const handleApplicationFinishClick = () => {
    api
      .put("customers", {
        identityNumber: identity,
        fullName: fullName,
        phoneNumber: phone,
        birthDate: birthDate,
        income: income,
      })
      .then((response) => {
        window.location.reload();
      })
      .catch((err) => {
        setOpen(true);
      });
  };
  return (
    <div
      style={{
        marginTop: "15vh",
        display: "inline-flex",
        flexDirection: "column",
      }}
    >
      <div>
        <Modal onClose={() => setOpen(false)} open={open} size="mini">
          <Modal.Content>
            <p>Müşteri güncellenirken bir hata oluştu</p>
          </Modal.Content>
          <Modal.Actions>
            <Button
              color="blue"
              onClick={() => {
                window.location.reload();
              }}
            >
              Tamam
            </Button>
          </Modal.Actions>
        </Modal>
      </div>
      <div
        style={{
          marginTop: "2vh",
        }}
      >
        <Input placeholder="T.C Kimlik No" onChange={handleIdentityChange} />
      </div>
      <div
        style={{
          marginTop: "2vh",
        }}
      >
        <Input placeholder="Tam Ad" onChange={handleFullNameChange} />
      </div>
      <div
        style={{
          marginTop: "2vh",
        }}
      >
        <Input
          type="number"
          placeholder="Telefon Numarası"
          onChange={handlePhoneNumberChange}
        />
      </div>
      <div
        style={{
          marginTop: "2vh",
        }}
      >
        <Input
          type="number"
          placeholder="Gelir"
          onChange={handleIncomeChange}
        />
      </div>
      <div
        style={{
          marginTop: "2vh",
        }}
      >
        <SemanticDatepicker
          size="small"
          locale="tr-TR"
          placeholder="Doğum Tarihi"
          onChange={handleBirthDateChange}
        />
      </div>
      <div
        style={{
          marginTop: "2vh",
        }}
      >
        <Button
          fluid
          color="blue"
          size="large"
          onClick={handleApplicationFinishClick}
        >
          Müşteri Güncelle
        </Button>
      </div>
    </div>
  );
};

export default CustomerUpdate;
