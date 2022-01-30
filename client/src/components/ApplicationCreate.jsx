import React from "react";
import SemanticDatepicker from "react-semantic-ui-datepickers";
import { Button, Dropdown, Input, Modal } from "semantic-ui-react";
import api from "../config/AxiosConfig";

const ApplicationCreate = () => {
  const [identity, setIdentity] = React.useState("");
  const [fullName, setFullName] = React.useState("");
  const [phone, setPhoneNumber] = React.useState("");
  const [income, setIncome] = React.useState(0);
  const [birthDate, setBirthDate] = React.useState("");
  const [depositValue, setDepositValue] = React.useState(0);
  const [depositType, setDepositType] = React.useState("");
  const [open, setOpen] = React.useState(false);
  const [resultOpen, setResultOpen] = React.useState(false);
  const [resultStatus, setResultStatus] = React.useState("");
  const [resultValue, setResultValue] = React.useState(0);

  const depositOptions = [
    {
      key: "HOUSE",
      text: "Ev",
      value: "HOUSE",
    },
    {
      key: "VEHICLE",
      text: "Araba",
      value: "VEHICLE",
    },
  ];

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

  const handleDepositTypeChange = (event, data) => {
    setDepositType(data.value);
  };

  const handleDepositValueChange = (e) => {
    setDepositValue(e.target.value);
  };

  const handleApplicationFinishClick = () => {
    api
      .post("applications", {
        identityNumber: identity,
        fullname: fullName,
        phoneNumber: phone,
        birthDate: birthDate,
        income: income,
        depositRequestDTO: { depositType: depositType, value: depositValue },
      })
      .then((response) => {
        if (response.status === "ACCEPTED") {
          setResultStatus("Onay");
        } else {
          setResultStatus("Red");
        }
        setResultValue(response.value);
        setResultOpen(true);
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
        <Modal
          onClose={() => setResultOpen(false)}
          open={resultOpen}
          size="mini"
        >
          <Modal.Content>
            <p>{"Başvuru Sonucu: " + resultStatus}</p>
          </Modal.Content>
          <Modal.Content>
            <p>{"Kredi Limiti: " + resultValue}</p>
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
      <div>
        <Modal onClose={() => setOpen(false)} open={open} size="mini">
          <Modal.Content>
            <p>Müşteri kaydedilirken bir hata oluştu</p>
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
      <div style={{ display: "flex", flexDirection: "row" }}>
        <Input
          placeholder="Teminat Değeri"
          onChange={handleDepositValueChange}
        />
        <Dropdown
          placeholder="Teminat Tipini Seçiniz"
          fluid
          selection
          options={depositOptions}
          onChange={handleDepositTypeChange}
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
          Kredi Başvurusunu Tamamla
        </Button>
      </div>
    </div>
  );
};

export default ApplicationCreate;
