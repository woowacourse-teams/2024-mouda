import ENDPOINTS from '../constants/endpoints';
import { useNavigate } from 'react-router-dom';

export default function TmpAddMoim() {
  const navigate = useNavigate();
  return (
    <>
      <button onClick={() => navigate(ENDPOINTS.main)}>메인으로</button>
    </>
  );
}
