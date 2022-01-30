import React from "react";
import ApplicationCheck from "../components/ApplicationCheck";
import ApplicationCreate from "../components/ApplicationCreate";
import ApplicationList from "../components/ApplicationList";
import CustomerCreate from "../components/CustomerCreate";
import CustomerDelete from "../components/CustomerDelete";
import CustomerUpdate from "../components/CustomerUpdate";
import Main from "../components/Main";

const UserPage = () => {
  const [pageNumber, setPageNumber] = React.useState(0);
  const [creditList, setCreditList] = React.useState([]);

  const handleButtonClick = (e) => {
    setPageNumber(Number(e.currentTarget.id));
  };

  return (
    <div
      style={{
        marginTop: "10vh",
        display: "inline-flex",
        flexDirection: "column",
      }}
    >
      {pageNumber === 0 && <Main handleButtonClick={handleButtonClick} />}
      {pageNumber === 1 && <CustomerCreate />}
      {pageNumber === 2 && <CustomerUpdate />}
      {pageNumber === 3 && <CustomerDelete />}
      {pageNumber === 4 && <ApplicationCreate />}
      {pageNumber === 5 && (
        <ApplicationCheck
          setCreditList={setCreditList}
          setPageNumber={setPageNumber}
        />
      )}
      {pageNumber === 6 && <ApplicationList creditList={creditList} />}
    </div>
  );
};

export default UserPage;
