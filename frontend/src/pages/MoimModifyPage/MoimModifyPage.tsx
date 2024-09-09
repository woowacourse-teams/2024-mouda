import { MoimInfo } from '@_types/index';
import { useLocation, useNavigate } from 'react-router-dom';

import Button from '@_components/Button/Button';
import FormLayout from '@_layouts/FormLayout/FormLayout';
import GET_ROUTES from '@_common/getRoutes';
import LabeledInput from '@_components/Input/MoimInput';
import MOIM_INPUT_INFOS from './MoimModifyPage.constant';
import useModifyMoim from '@_hooks/mutaions/useModifyMoim';
import useMoimInfoInput from './MoimModifyPage.hook';
import { useState } from 'react';
import LabeledTextArea from '@_components/TextArea/LabeledTextArea';

export default function MoimModifyPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state as MoimInfo;
  const { mutate: modifyMoim } = useModifyMoim((moimId: number) => {
    navigate(GET_ROUTES.nowDarakbang.moimDetail(moimId));
  });
  const [isSubmitted, setIsSubmitted] = useState(false);

  const {
    inputData,
    handleInputChange,
    handleTextAreaChange,
    isValidMoimInfoInput,
  } = useMoimInfoInput(state);

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
        {MOIM_INPUT_INFOS.map((info) => {
          if (info.type === 'textarea') {
            return (
              <LabeledTextArea
                {...info}
                key={info.title}
                onChange={handleTextAreaChange}
                value={inputData[info.name]}
                validateFun={info?.validate}
              />
            );
          } else if (info.type === 'time') {
            return (
              <LabeledInput
                {...info}
                key={info.title}
                type="time"
                onChange={handleInputChange}
                value={inputData[info.name]}
                validateFun={(time: string) => {
                  return info?.validate
                    ? info.validate(time, inputData['date'])
                    : true;
                }}
              />
            );
          }

          return (
            <LabeledInput
              {...info}
              key={info.title}
              onChange={handleInputChange}
              value={inputData[info.name]}
              validateFun={info?.validate}
            />
          );
        })}
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
