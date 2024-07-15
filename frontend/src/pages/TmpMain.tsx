import ENDPOINTS from '../constants/endpoints';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';

export default function TmpMain() {
  const navigate = useNavigate();
  const { data } = useQuery({
    queryKey: ['aaa'],
    queryFn: async () => {
      const response = await fetch(
        'https://jsonplaceholder.typicode.com/posts/1',
      );
      return await response.json();
    },
  });

  console.log(data);
  return (
    <>
      <button onClick={() => navigate(ENDPOINTS.addMoim)}>모임추가로</button>
    </>
  );
}
