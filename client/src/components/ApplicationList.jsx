import React from "react";
import { Card } from "semantic-ui-react";

const ApplicationList = (props) => {
  return (
    <div>
      {props.creditList.map((element, i) => {
        return (
          <div>
            <Card>
              <Card.Content>
                <Card.Header>{element.status}</Card.Header>
                <Card.Header>{element.value}</Card.Header>
              </Card.Content>
            </Card>
          </div>
        );
      })}
    </div>
  );
};

export default ApplicationList;
