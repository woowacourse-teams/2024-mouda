import { ChangeEvent, useState } from 'react';
import {
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimModifyPage.util';

import { TempMoimInputInfo } from '../../types';

const useMoimInfoInput = (state: TempMoimInputInfo) => {
  const [inputData, setInputData] = useState<TempMoimInputInfo>({
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
    validateDate(inputData.date) &&
    validateTime(inputData.time) &&
    validatePlace(inputData.place) &&
    validateMaxPeople(inputData.maxPeople);

  return {
    inputData,
    handleChange,
    isValidMoimInfoInput,
  };
};

export default useMoimInfoInput;
