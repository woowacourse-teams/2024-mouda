import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import ROUTES from '@_constants/routes';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { useLocation, useNavigate, useNavigationType } from 'react-router-dom';
import TitleStep from './Steps/TitleStep';
// import OfflineOrOnlineStep from './Steps/OfflineOrOnlineStep';
import PlaceStep from './Steps/PlaceStep';
import DateAndTimeStep from './Steps/DateAndTimeStep';
import MaxPeopleStep from './Steps/MaxPeopleStep';
import DescriptionStep from './Steps/DescriptionStep';
import useMoimCreationForm from './MoimCreationPage.hook';
import POLICES from '@_constants/poclies';
import useAddMoim from '@_hooks/mutaions/useAddMoim';
import { isApprochedByUrl } from './MoimCreatePage.util';

export type MoimCreationStep =
  | '이름입력'
  // | '오프라인/온라인선택'
  | '장소선택'
  | '날짜/시간설정'
  | '최대인원설정'
  | '설명입력';

export const steps: MoimCreationStep[] = [
  '이름입력',
  // '오프라인/온라인선택',
  '장소선택',
  '날짜/시간설정',
  '최대인원설정',
  '설명입력',
];

const inputKeyMapper = {
  title: '이름입력',
  // offlineOrOnline: '오프라인/온라인선택',
  place: '장소선택',
  date: '날짜설정',
  time: '시간설정',
  maxPeople: '최대인원설정',
  description: '설명입력',
};

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const navigationType = useNavigationType();

  const currentStep = location.state?.step || steps[0];

  const isNewMoimCreation =
    currentStep === '이름입력' &&
    (navigationType === 'PUSH' || isApprochedByUrl());

  const {
    formData,
    formValidation,
    updateTitle,
    // updateOfflineOrOnline,
    updatePlace,
    updateDate,
    updateTime,
    updateMaxPeople,
    updateDescription,
  } = useMoimCreationForm(isNewMoimCreation);

  const { mutate: addMoim } = useAddMoim((moimId: number) => {
    navigate(`/moim/${moimId}`);
  });

  const currentComponents: {
    main: JSX.Element;
    footer: JSX.Element;
  } = { main: <></>, footer: <></> };

  if (currentStep === '이름입력') {
    currentComponents.main = (
      <TitleStep title={formData.title} onTitleChange={updateTitle} />
    );
    currentComponents.footer = (
      <FunnelButton
        disabled={!formValidation.title}
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '장소선택' },
          });
        }}
      >
        {formData.title === ''
          ? '모임 이름을 입력해주세요'
          : !formValidation.title
            ? `${POLICES.minimumTitleLength} ~ ${POLICES.maximumTitleLength}글자만 가능해요`
            : '다음으로'}
      </FunnelButton>
    );
    // } else if (currentStep === '오프라인/온라인선택') {
    //   currentComponents.main = (
    //     <OfflineOrOnlineStep
    //       offlineOrOnline={moimInfo.offlineOrOnline}
    //       onOfflineOrOnlineChange={handleOfflineOrOnlineChange}
    //     />
    //   );
    //   currentComponents.footer = (
    //     <FunnelButton
    //       onClick={() => {
    //         navigate(ROUTES.addMoim, {
    //           state: {
    //             step: moimInfo.offlineOrOnline ? '장소선택' : '날짜/시간설정',
    //           },
    //         });
    //       }}
    //     >
    //       {moimInfo.offlineOrOnline === ''
    //         ? '스킵하고 채팅에서 정할게요!'
    //         : '다음으로'}
    //     </FunnelButton>
    //   );
  } else if (currentStep === '장소선택') {
    currentComponents.main = (
      <PlaceStep
        // offlineOrOnline={moimInfo.offlineOrOnline}
        place={formData.place}
        onPlaceChange={updatePlace}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        disabled={!formValidation.place}
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '날짜/시간설정' },
          });
        }}
      >
        {formData.place === ''
          ? '스킵하고 채팅에서 정할게요!'
          : !formValidation.place
            ? `${POLICES.minimumPlaceLength} ~ ${POLICES.maximumPlaceLength}글자만 가능해요`
            : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '날짜/시간설정') {
    currentComponents.main = (
      <DateAndTimeStep
        date={formData.date}
        time={formData.time}
        onDateChange={updateDate}
        onTimeChange={updateTime}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        disabled={!formValidation.date || !formValidation.time}
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '최대인원설정' },
          });
        }}
      >
        {!formValidation.date && !formValidation.time
          ? '날짜와 시간을 다시 확인해주세요'
          : !formValidation.date
            ? '날짜를 다시 확인해주세요'
            : !formValidation.time
              ? '시간을 다시 확인해주세요'
              : formData.date === '' && formData.time === ''
                ? '스킵하고 채팅에서 정할게요!'
                : formData.date === ''
                  ? '날짜는 채팅에서 정할게요!'
                  : formData.time === ''
                    ? '시간은 채팅에서 정할게요!'
                    : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '최대인원설정') {
    currentComponents.main = (
      <MaxPeopleStep
        maxPeople={formData.maxPeople}
        onMaxPeopleChange={updateMaxPeople}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        disabled={!formValidation.maxPeople}
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '설명입력' },
          });
        }}
      >
        {!formValidation.maxPeople
          ? `${POLICES.minimumMaxPeople} ~ ${POLICES.maximumMaxPeople}명만 가능해요`
          : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '설명입력') {
    currentComponents.main = (
      <DescriptionStep
        description={formData.description}
        onDescriptionChange={updateDescription}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        onClick={() => {
          const invalidInputKeys = Object.entries(formValidation)
            .filter(([, value]) => !value)
            .map(([key]) => key);
          if (invalidInputKeys.length > 0) {
            const invalidKeys = invalidInputKeys
              .map((key) => inputKeyMapper[key as keyof typeof inputKeyMapper])
              .join(', ');
            alert(`${invalidKeys}이 올바르지 않습니다.`);
            return;
          }
          addMoim(formData);
        }}
      >
        끗!
      </FunnelButton>
    );
  }

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton
            onClick={() => {
              navigate(-1);
            }}
          />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>모임 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelStepIndicator totalSteps={steps} currentStep={currentStep} />

      <FunnelLayout.Main>{currentComponents?.main}</FunnelLayout.Main>

      <FunnelLayout.Footer>{currentComponents?.footer}</FunnelLayout.Footer>
    </FunnelLayout>
  );
}
