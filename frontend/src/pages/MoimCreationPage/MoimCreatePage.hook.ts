// import { ChangeEvent, useState } from 'react';
// import {
//   validateDate,
//   validateMaxPeople,
//   validatePlace,
//   validateTime,
//   validateTitle,
// } from './MoimCreatePage.util';

// import { MoimInputInfo } from '../../types';
import { useLocation, useNavigationType } from 'react-router-dom';
import { MoimCreationStep, steps } from './MoimCreationPage';
import useStatePersist from '@_hooks/useStatePersist';
import { MoimInputInfo } from '@_types/index';

// const useMoimInfoInput = () => {
//   const [inputData, setInputData] = useState<MoimInputInfo>({
//     title: '',
//     date: '',
//     time: '',
//     place: '',
//     maxPeople: 0,
//     description: '',
//   });

//   const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
//     setInputData({
//       ...inputData,
//       [e.target.name]: e.target.value,
//     });
//   };

//   const handleTextAreaChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
//     setInputData({
//       ...inputData,
//       [e.target.name]: e.target.value,
//     });
//   };

//   const isValidMoimInfoInput =
//     validateTitle(inputData.title) &&
//     (inputData.date === '' || validateDate(inputData.date)) &&
//     (inputData.time === '' || validateTime(inputData.time)) &&
//     (inputData.place === '' || validatePlace(inputData.place)) &&
//     validateMaxPeople(inputData.maxPeople);

//   return {
//     inputData,
//     handleInputChange,
//     handleTextAreaChange,
//     isValidMoimInfoInput,
//   };
// };

// export default useMoimInfoInput;

type MoimCreationInputInfo = MoimInputInfo & {
  offlineOrOnline: string;
  description: string;
};

const useMoimCreationInfo = () => {
  const location = useLocation();
  const navigationType = useNavigationType();
  const currentStep: MoimCreationStep = location.state?.step || steps[0];

  const isStartOfMoimCreation =
    currentStep === '이름입력' && navigationType === 'PUSH';
  if (isStartOfMoimCreation) {
    sessionStorage.removeItem('moimCreationInfo');
  }

  const [moimInfo, setMoimInfo] = useStatePersist<MoimCreationInputInfo>({
    key: 'moimCreationInfo',
    initialState: {
      title: '',
      offlineOrOnline: '',
      place: '',
      date: '',
      time: '',
      maxPeople: 0,
      description: '',
    },
    storage: 'sessionStorage',
  });

  return { currentStep, moimInfo, setMoimInfo };
};

export default useMoimCreationInfo;
