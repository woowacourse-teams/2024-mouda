import useJoinMoim from '@_hooks/mutaions/useJoinMoim';
import useMoim from '@_hooks/queries/useMoim';
import { useParams } from 'react-router-dom';

export default function TempDetailPage() {
  const params = useParams();
  const moimId = Number(params.moimId);

  const { moim, isLoading } = useMoim(moimId);
  const { mutate } = useJoinMoim(() => {
    alert('참여했습니다.');
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (!moim) {
    return <div>No data found</div>;
  }

  // moim 데이터를 출력합니다.
  return (
    <div>
      <h1>{moim.title}</h1>
      <p>{moim.description}</p>
      <p>Place: {moim.place}</p>
      <p>Date: {moim.date}</p>
      <p>Time: {moim.time}</p>
      <p>Max People: {moim.maxPeople}</p>
      <p>currentPeople: {moim.currentPeople}</p>
      <p>Author: {moim.authorNickname}</p>

      <button onClick={() => mutate(moimId)}>참여하기</button>
    </div>
  );
}
