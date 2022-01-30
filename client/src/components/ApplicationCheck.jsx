import React from "react";
import SemanticDatepicker from "react-semantic-ui-datepickers";
import { Button, Input, Modal } from "semantic-ui-react";
import api from "../config/AxiosConfig";

const ApplicationCheck = (props) => {
  const [identity, setIdentity] = React.useState("");
  const [birthDate, setBirthDate] = React.useState("");
  const [open, setOpen] = React.useState(false);

  const splitDate = (data) => {
    var res = data.split("-");
    return res[2] + "-" + res[1] + "-" + res[0];
  };

  const handleIdentityChange = (e) => {
    setIdentity(e.currentTarget.value);
  };

  const handleBirthDateChange = (event, data) => {
    setBirthDate(
      splitDate(data.value.toLocaleDateString().replaceAll(".", "-"))
    );
  };

  const handleResultList = async () => {
    var result = [];
    await api
      .get("applications/user/" + identity + "?birthDate=" + birthDate)
      .then((response) => {
        response.map((e) => {
          result.push({
            status: e.status,
            value: e.value,
          });
        });
        props.setCreditList(result);
        props.setPageNumber(6);
      })
      .catch((err) => {
        setOpen(true);
      });

    return result;
  };

  const handleApplicationFinishClick = () => {
    handleResultList();
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
        <Input
          type="number"
          placeholder="T.C Kimlik No"
          onChange={handleIdentityChange}
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
          Kredi Başvuru Sorgula
        </Button>
      </div>
    </div>
  );
};

export default ApplicationCheck;
