import GET_ROUTES from '@_common/getRoutes';
import POLICES from '@_constants/poclies';
import useAddBet from '@_hooks/mutaions/useAddBet';
import useStatePersist from '@_hooks/useStatePersist';
import { BetInputInfo } from '@_types/index';
import { isApproachedByUrl } from '@_utils/isApproachedByUrl';
import { useNavigate, useNavigationType } from 'react-router-dom';
import { BetCreationStep } from '../BetCreationPage';
import { useEffect } from 'react';

export default function useBetCreationForm(currentStep: BetCreationStep) {
  const navigate = useNavigate();
  const navigationType = useNavigationType();

  const isNewBetCreation =
    currentStep === '제목' &&
    (navigationType === 'PUSH' || isApproachedByUrl());

  if (isNewBetCreation) {
    sessionStorage.removeItem('betCreationInfo');
    sessionStorage.removeItem('betCreationIsValid');
    sessionStorage.removeItem('betCreationErrorMessage');
  }

  useEffect(() => {
    // 뒤로가기를 통해 만들기 페이지에 오는 경우 메인 페이지로 이동
    if (currentStep === '추첨시간' && navigationType === 'POP') {
      navigate(GET_ROUTES.nowDarakbang.bet());
    }
  }, [currentStep, navigationType, navigate]);

  const [form, setState] = useStatePersist<BetInputInfo>({
    key: 'betCreationInfo',
    initialState: {
      title: '',
      waitingMinutes: 0,
    },
  });
  const [isValid, setIsValid] = useStatePersist({
    key: 'betCreationIsValid',
    initialState: {
      title: false,
      waitingMinutes: false,
    },
  });
  const [errorMessage, setErrorMessage] = useStatePersist({
    key: 'betCreationErrorMessage',
    initialState: {
      title: '',
      waitingMinutes: '',
    },
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
