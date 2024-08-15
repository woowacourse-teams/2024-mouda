import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';

export default function PlaceStep() {
  return (
    <>
      <FunnelQuestion>
        <FunnelQuestion.Highlight>어디</FunnelQuestion.Highlight>
        <FunnelQuestion.Text>서 </FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Text>모이나요?</FunnelQuestion.Text>
      </FunnelQuestion>
    </>
  );
}
