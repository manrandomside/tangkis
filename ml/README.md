# ml

Ruang kerja tim AI untuk melatih model pendeteksi pesan penipuan.

Rencana isi folder:

| Folder / file | Isi |
|---|---|
| `notebooks/` | Notebook Google Colab: eksplorasi data, preprocessing, pelatihan, evaluasi |
| `data/` | Dataset yang sudah dibersihkan (tanpa data pribadi asli) |
| `export/` | Model `.tflite` dan kosakata `.json` yang disalin ke aplikasi Android |

Metode: Jaringan Syaraf Tiruan jenis Multilayer Perceptron yang dilatih dengan algoritma Backpropagation, dibuat dengan Python dan TensorFlow/Keras.

Dataset utama: [SMS Spam Indonesia](https://gist.github.com/agtbaskara/a1a7017027cc1df9d35cf06e1e5575b7) (label 0 = normal, 1 = penipuan, 2 = promo).

Aturan data: nama, nomor telepon, dan nomor rekening pada contoh pesan tambahan wajib disamarkan sebelum disimpan di repository.
