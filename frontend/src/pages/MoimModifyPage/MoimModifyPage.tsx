import Button from '@_components/Button/Button';
import FormLayout from '@_layouts/FormLayout/FormLayout';
import LabeledInput from '@_components/Input/MoimInput';
import MOIM_INPUT_INFOS from './MoimModifyPage.constant';
import useMoimInfoInput from './MoimModifyPage.hook';
import { useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import useModifyMoim from '@_hooks/mutaions/useModifyMoim';
import { MoimInfo, MoimInputInfo } from '@_types/index';

export default function MoimModifyPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state as MoimInfo;
  const { mutate: modifyMoim } = useModifyMoim((moimId: number) => {
    navigate(`/moim/${moimId}`);
  });
  const [isSubmitted, setIsSubmitted] = useState(false);

  const { inputData, handleChange, isValidMoimInfoInput } =
    useMoimInfoInput(state);

  const handleRegisterButtonClick = async () => {
    if (!isValidMoimInfoInput) {
      return;
    }
    if (isSubmitted) return;
    setIsSubmitted(true);
    modifyMoim({ moimId: state.moimId, state: inputData });
  };

  return (
    <FormLayout>
      <FormLayout.Header onBackArrowClick={() => navigate(-1)}>
        모임 수정하기
      </FormLayout.Header>

      <FormLayout.MainForm>
        {MOIM_INPUT_INFOS.map((info) => (
          <LabeledInput
            {...info}
            key={info.title}
            onChange={handleChange}
            value={inputData[info.name as keyof MoimInputInfo]}
          />
        ))}
      </FormLayout.MainForm>

      <FormLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          onClick={handleRegisterButtonClick}
          disabled={!isValidMoimInfoInput}
        >
          등록하기
        </Button>
        i
      </FormLayout.BottomButtonWrapper>
    </FormLayout>
  );
}
