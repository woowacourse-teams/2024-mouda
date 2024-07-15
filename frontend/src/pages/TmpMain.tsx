import ENDPOINTS from '../constants/endpoints';
import { useNavigate } from 'react-router-dom';

export default function TmpMain() {
  const navigate = useNavigate();
  return (
    <>
      <button onClick={() => navigate(ENDPOINTS.addMoim)}>모임추가로</button>
    </>
  );
}
