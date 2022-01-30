import React from "react";
import { Button, Input, Modal } from "semantic-ui-react";
import api from "../config/AxiosConfig";

const CustomerDelete = () => {
  const [identity, setIdentity] = React.useState("");
  const [open, setOpen] = React.useState(false);

  const handleIdentityChange = (e) => {
    setIdentity(e.currentTarget.value);
  };

  const handleApplicationFinishClick = () => {
    api
      .delete("customers/" + identity)
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
            <p>Müşteri silinirken bir hata oluştu</p>
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
        <Button
          fluid
          color="blue"
          size="large"
          onClick={handleApplicationFinishClick}
        >
          Müşteri Sil
        </Button>
      </div>
    </div>
  );
};

export default CustomerDelete;
