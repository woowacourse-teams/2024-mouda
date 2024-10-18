import GET_ROUTES from '@_common/getRoutes';
import POLICES from '@_constants/poclies';
import useAddBet from '@_hooks/mutaions/useAddBet';
import { BetInputInfo } from '@_types/index';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function useBetCreationForm() {
  const navigate = useNavigate();

  const [form, setState] = useState<BetInputInfo>({
    title: '',
    waitingMinutes: 0,
  });
  const [isValid, setIsValid] = useState({
    title: false,
    waitingMinutes: false,
  });
  const [errorMessage, setErrorMessage] = useState({
    title: '',
    waitingMinutes: '',
  });

  const updateTitle = (title: string) => {
    setState({ ...form, title });
  };

  const updateWaitingMinutes = (waitingMinutes: number) => {
    setState({ ...form, waitingMinutes });
  };

  const validateTitle = (title: string) => {
    if (
      title.length < POLICES.minBetTitleLength ||
      title.length > POLICES.maxBetTitleLength
    ) {
      setIsValid((prev) => ({ ...prev, title: false }));
      setErrorMessage((prev) => ({
        ...prev,
        title: '제목은 1자 이상 20자 이하로 입력해주세요.',
      }));
      return false;
    }

    setIsValid((prev) => ({ ...prev, title: true }));
    setErrorMessage((prev) => ({ ...prev, title: '' }));
    return true;
  };

  const validateWaitingMinutes = (waitingMinutes: number) => {
    if (!POLICES.betWaitingMinutesOptions.includes(waitingMinutes)) {
      setIsValid((prev) => ({ ...prev, waitingMinutes: false }));
      setErrorMessage((prev) => ({
        ...prev,
        waitingMinutes: '추첨 시간을 선택해주세요.',
      }));
      return false;
    }

    setIsValid((prev) => ({ ...prev, waitingMinutes: true }));
    setErrorMessage((prev) => ({ ...prev, waitingMinutes: '' }));
    return true;
  };

  const finalValidate = (form: BetInputInfo) => {
    const isTitleValid = validateTitle(form.title);
    const isWaitingMinutesValid = validateWaitingMinutes(form.waitingMinutes);

    return isTitleValid && isWaitingMinutesValid;
  };

  const { mutateAsync: createBet } = useAddBet((betId) => {
    navigate(GET_ROUTES.nowDarakbang.betDetail(betId));
  });

  return {
    form,
    isValid,
    errorMessage,
    updateTitle,
    updateWaitingMinutes,
    validateTitle,
    validateWaitingMinutes,
    finalValidate,
    createBet,
  };
}
