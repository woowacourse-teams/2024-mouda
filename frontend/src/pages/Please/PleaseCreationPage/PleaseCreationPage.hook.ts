import { ChangeEvent, useState } from 'react';
import { validateDescription, validateTitle } from './PleaseCreationPage.util';
import { PleaseInfoInput } from '@_types/index';

const usePleaseInfoInput = () => {
  const [inputData, setInputData] = useState<PleaseInfoInput>({
    title: '',
    description: '',
  });

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const handleTextAreaChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const isValidPleaseInfoInput =
    validateTitle(inputData.title) &&
    validateDescription(inputData.description);

  return {
    inputData,
    handleInputChange,
    handleTextAreaChange,
    isValidPleaseInfoInput,
  };
};

export default usePleaseInfoInput;
