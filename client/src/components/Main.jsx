import React from "react";
import { Button } from "semantic-ui-react";

const Main = (props) => {
  return (
    <div>
      <div
        style={{
          marginTop: "5vh",
        }}
      >
        <Button
          id="1"
          fluid
          color="blue"
          size="large"
          onClick={props.handleButtonClick}
        >
          Müşteri Kaydet
        </Button>
      </div>
      <div
        style={{
          marginTop: "5vh",
        }}
      >
        <Button
          id="2"
          fluid
          color="blue"
          size="large"
          onClick={props.handleButtonClick}
        >
          Müşteri Güncelle
        </Button>
      </div>
      <div
        style={{
          marginTop: "5vh",
        }}
      >
        <Button
          id="3"
          fluid
          color="blue"
          size="large"
          onClick={props.handleButtonClick}
        >
          Müşteri Sil
        </Button>
      </div>
      <div
        style={{
          marginTop: "5vh",
        }}
      >
        <Button
          id="4"
          fluid
          color="blue"
          size="large"
          onClick={props.handleButtonClick}
        >
          Kredi Başvurusu
        </Button>
      </div>
      <div
        style={{
          marginTop: "5vh",
        }}
      >
        <Button
          id="5"
          fluid
          color="blue"
          size="large"
          onClick={props.handleButtonClick}
        >
          Kredi Sorgula
        </Button>
      </div>
    </div>
  );
};

export default Main;
