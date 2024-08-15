import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';

export default function MoimCreationPage() {
  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>모임 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Text>모임의 </FunnelQuestion.Text>
          <FunnelQuestion.Highlight>이름</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>은 무엇인가요?</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelInput placeholder="모임의 이름을 입력해주세요." />
      </FunnelLayout.Main>
      <FunnelLayout.Footer>
        <FunnelStepIndicator totalSteps={5} currentStep={1} />
        <FunnelButton onClick={() => {}}>다음으로</FunnelButton>
      </FunnelLayout.Footer>
    </FunnelLayout>
  );
}
