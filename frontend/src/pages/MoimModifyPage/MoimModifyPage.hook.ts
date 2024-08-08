import { ChangeEvent, useState } from 'react';
import {
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimModifyPage.util';

import { MoimInputInfo } from '../../types';

const useMoimInfoInput = (state: MoimInputInfo) => {
  const [inputData, setInputData] = useState<MoimInputInfo>({
    title: state.title,
    date: state.date,
    time: state.time,
    place: state.place,
    maxPeople: state.maxPeople,
    description: state.description,
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const isValidMoimInfoInput =
    validateTitle(inputData.title) &&
    (inputData.date === '' || validateDate(inputData.date)) &&
    (inputData.time === '' || validateTime(inputData.time)) &&
    (inputData.place === '' || validatePlace(inputData.place)) &&
    validateMaxPeople(inputData.maxPeople);

  return {
    inputData,
    handleChange,
    isValidMoimInfoInput,
  };
};

export default useMoimInfoInput;
