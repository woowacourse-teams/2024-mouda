import { ChangeEvent, useState } from 'react';
import { validateDescription, validateTitle } from './PleaseCreationPage.util';
import { PleaseInfoInput } from '@_types/index';

const usePleaseInfoInput = () => {
  const [inputData, setInputData] = useState<PleaseInfoInput>({
    title: '',
    description: '',
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const isValidMoimInfoInput =
    validateTitle(inputData.title) &&
    validateDescription(inputData.description);

  return {
    inputData,
    handleChange,
    isValidMoimInfoInput,
  };
};

export default usePleaseInfoInput;
